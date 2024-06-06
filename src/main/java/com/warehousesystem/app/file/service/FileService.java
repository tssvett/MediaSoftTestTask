package com.warehousesystem.app.file.service;

import dev.tssvett.handler.exception.NotFoundByIdException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public interface FileService {

    String upload(UUID productId, MultipartFile file) throws NotFoundByIdException, IOException;

    void download(UUID productId, OutputStream outputStream) throws IOException;
}
