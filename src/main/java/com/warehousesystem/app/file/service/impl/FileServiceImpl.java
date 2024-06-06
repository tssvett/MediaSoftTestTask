package com.warehousesystem.app.file.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.warehousesystem.app.file.config.AwsConfigurationProperties;
import com.warehousesystem.app.file.service.FileService;
import com.warehousesystem.app.model.File;
import com.warehousesystem.app.repository.FileRepository;
import com.warehousesystem.app.repository.ProductRepository;
import dev.tssvett.handler.exception.NotFoundByIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.zip.ZipOutputStream;
import java.util.zip.ZipEntry;


@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final AmazonS3 amazonS3;
    private final AwsConfigurationProperties awsConfigurationProperties;
    private final ProductRepository productRepository;
    private final FileRepository fileRepository;

    @Transactional
    @Override
    public String upload(UUID productId, MultipartFile file) throws NotFoundByIdException, IOException {
        Path tempFile = Files.createTempFile("upload-", file.getOriginalFilename());
        UUID key = UUID.randomUUID();
        try {
            file.transferTo(tempFile.toFile());
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.addUserMetadata("filename", file.getOriginalFilename());
            PutObjectRequest request = new PutObjectRequest(
                    awsConfigurationProperties.getBucket(),
                    key.toString(),
                    tempFile.toFile()
            ).withMetadata(metadata);
            amazonS3.putObject(request);

            File fileImage = new File(key, productRepository.getReferenceById(productId));
            fileRepository.save(fileImage);
        } finally {
            Files.delete(tempFile);
        }
        return key.toString();
    }

    @Transactional
    @Override
    public void download(UUID productId, OutputStream outputStream) throws IOException {
        List<File> files = fileRepository.findByProductId(productId);
        Set<String> usedFileNames = new HashSet<>();
        try (ZipOutputStream zos = new ZipOutputStream(outputStream)) {
            for (File file : files) {
                UUID key = file.getId();
                S3Object s3Object = amazonS3.getObject(new GetObjectRequest(awsConfigurationProperties.getBucket(), key.toString()));
                try (InputStream inputStream = s3Object.getObjectContent()) {
                    String fileName = s3Object.getObjectMetadata().getUserMetaDataOf("filename");
                    if (fileName == null) {
                        fileName = key.toString();
                    }
                    fileName = getUniqueFileName(fileName, usedFileNames);
                    ZipEntry zipEntry = new ZipEntry(fileName);
                    zos.putNextEntry(zipEntry);
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = inputStream.read(buffer)) > 0) {
                        zos.write(buffer, 0, length);
                    }
                    zos.closeEntry();
                }
            }
        }


    }

    private String getUniqueFileName(String fileName, Set<String> usedFileNames) {
        if (usedFileNames.contains(fileName)) {
            int extensionIndex = fileName.lastIndexOf('.');
            String extension = extensionIndex == -1 ? "" : fileName.substring(extensionIndex);
            fileName = fileName.substring(0, extensionIndex == -1 ? fileName.length() : extensionIndex);
            int counter = 1;
            while (usedFileNames.contains(fileName + "(" + counter + ")" + extension)) {
                counter++;
            }
            return fileName + "(" + counter + ")" + extension;
        }
        return fileName;
    }
}
