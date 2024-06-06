package com.warehousesystem.app.controller;

import com.warehousesystem.app.dto.product.ProductCreateDto;
import com.warehousesystem.app.dto.product.ProductFullDto;
import com.warehousesystem.app.dto.product.ProductSearchDto;
import com.warehousesystem.app.dto.product.ProductUpdateDto;
import com.warehousesystem.app.file.service.FileService;
import com.warehousesystem.app.service.ProductService;
import dev.tssvett.handler.exception.EmptyProductException;
import dev.tssvett.handler.exception.NotFoundByArticleException;
import dev.tssvett.handler.exception.NotFoundByIdException;
import dev.tssvett.handler.exception.SQLUniqueException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

@RestController
@Validated
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService warehouseGoodService;
    private final FileService fileStorage;

    @GetMapping("/goods")
    public ResponseEntity<List<ProductFullDto>> getGoodsAll(@Valid @RequestBody ProductSearchDto warehouseGoodSearchDto) throws EmptyProductException {
        final List<ProductFullDto> goods = warehouseGoodService.readAll(warehouseGoodSearchDto);
        return new ResponseEntity<>(goods, HttpStatus.OK);
    }

    @PostMapping("/goods")
    public ResponseEntity<ProductFullDto> createGood(@Valid @RequestBody ProductCreateDto warehouseGood) throws SQLUniqueException {
        ProductFullDto good = warehouseGoodService.create(warehouseGood);
        return new ResponseEntity<>(good, HttpStatus.CREATED);
    }

    @DeleteMapping("/goods")
    public ResponseEntity<ProductFullDto> deleteAllGoods() throws EmptyProductException, NotFoundByArticleException {
        warehouseGoodService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/goodsById")
    public ResponseEntity<ProductFullDto> getGoodById(@RequestParam(value = "id") @Valid @PathVariable UUID id) throws NotFoundByIdException {
        final ProductFullDto good = warehouseGoodService.readById(id);
        return new ResponseEntity<>(good, HttpStatus.OK);
    }

    @Transactional
    @PutMapping("/goodsById")
    public ResponseEntity<ProductFullDto> updateGoodById(@RequestParam(value = "id") @Valid @PathVariable UUID id,
                                                         @Valid @RequestBody ProductUpdateDto good) throws NotFoundByIdException, SQLUniqueException {
        ProductFullDto newGood = warehouseGoodService.updateById(good, id);
        return new ResponseEntity<>(newGood, HttpStatus.OK);
    }


    @Transactional
    @DeleteMapping("/goodsById")
    public ResponseEntity<ProductFullDto> deleteGoodById(@RequestParam(value = "id") @Valid @PathVariable UUID id) throws NotFoundByIdException {
        warehouseGoodService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/goodsByArticle")
    public ResponseEntity<ProductFullDto> getGoodByArticle(@RequestParam(value = "article") @Valid @PathVariable String article) throws NotFoundByArticleException {
        final ProductFullDto good = warehouseGoodService.readByArticle(article);
        return new ResponseEntity<>(good, HttpStatus.OK);
    }


    @Transactional
    @PutMapping("/goodsByArticle")
    public ResponseEntity<ProductFullDto> updateGoodByArticle(@RequestParam(value = "article") @Valid @PathVariable String name, @RequestBody @Valid ProductUpdateDto goodBody) throws NotFoundByArticleException, SQLUniqueException {
        ProductFullDto good = warehouseGoodService.updateByArticle(goodBody, name);
        return new ResponseEntity<>(good, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/goodsByArticle")
    public ResponseEntity<ProductFullDto> deleteGoodByArticle(@RequestParam(value = "article") @Valid @PathVariable String name) throws NotFoundByArticleException {
        warehouseGoodService.deleteByArticle(name);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{productId}/upload")
    public String uploadFile(@PathVariable UUID productId, @RequestParam("file") MultipartFile file) throws NotFoundByIdException, IOException {
        String key = fileStorage.upload(productId, file);
        return key;
    }

    @GetMapping("/{productId}/download")
    public void downloadFile(@PathVariable UUID productId, HttpServletResponse response) {
        response.setHeader("Content-Disposition", "attachment; filename=\"images.zip\"");
        response.setHeader("Content-Type", "application/zip");
        try (OutputStream outputStream = response.getOutputStream()) {
            fileStorage.download(productId, outputStream);
        } catch (Exception e) {
            log.error("Error while downloading file", e);
        }
    }
}
