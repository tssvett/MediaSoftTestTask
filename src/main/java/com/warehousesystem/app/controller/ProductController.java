package com.warehousesystem.app.controller;


import com.warehousesystem.app.dto.ProductFullDto;
import com.warehousesystem.app.dto.product.ProductCreateDto;
import com.warehousesystem.app.dto.product.ProductSearchDto;
import com.warehousesystem.app.dto.product.ProductUpdateDto;
import com.warehousesystem.app.handler.exception.EmptyProductException;
import com.warehousesystem.app.handler.exception.NotFoundByArticleException;
import com.warehousesystem.app.handler.exception.NotFoundByIdException;
import com.warehousesystem.app.handler.exception.SQLUniqueException;
import com.warehousesystem.app.service.ProductService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@Validated
@CrossOrigin
@RequestMapping("/warehouse")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @GetMapping("/goods")
    public ResponseEntity<List<ProductFullDto>> getGoodsAll(@Valid @RequestBody ProductSearchDto productSearchDto) throws EmptyProductException {
        final List<ProductFullDto> productFullDtoList = productService.readAll(productSearchDto);
        return new ResponseEntity<>(productFullDtoList, HttpStatus.OK);
    }

    @PostMapping("/goods")
    public ResponseEntity<ProductFullDto> createGood(@Valid @RequestBody ProductCreateDto productCreateDto) throws SQLUniqueException {
        ProductFullDto productFullDto = productService.create(productCreateDto);
        return new ResponseEntity<>(productFullDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/goods")
    public ResponseEntity<ProductFullDto> deleteAllGoods() throws EmptyProductException, NotFoundByArticleException {
        productService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/goodsById")
    public ResponseEntity<ProductFullDto> getGoodById(@RequestParam(value = "id") @Valid @PathVariable UUID id) throws NotFoundByIdException {
        final ProductFullDto productFullDto = productService.readById(id);
        return new ResponseEntity<>(productFullDto, HttpStatus.OK);
    }

    @Transactional
    @PutMapping("/goodsById")
    public ResponseEntity<ProductFullDto> updateGoodById(@RequestParam(value = "id") @Valid @PathVariable UUID id,
                                                         @Valid @RequestBody ProductUpdateDto productUpdateDto) throws NotFoundByIdException, SQLUniqueException {
        ProductFullDto productFullDto = productService.updateById(productUpdateDto, id);
        return new ResponseEntity<>(productFullDto, HttpStatus.OK);
    }


    @Transactional
    @DeleteMapping("/goodsById")
    public ResponseEntity<ProductFullDto> deleteGoodById(@RequestParam(value = "id") @Valid @PathVariable UUID id) throws NotFoundByIdException {
        productService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/goodsByArticle")
    public ResponseEntity<ProductFullDto> getGoodByArticle(@RequestParam(value = "article") @Valid @PathVariable String article) throws NotFoundByArticleException {
        final ProductFullDto productFullDto = productService.readByArticle(article);
        return new ResponseEntity<>(productFullDto, HttpStatus.OK);
    }

    @Transactional
    @PutMapping("/goodsByArticle")
    public ResponseEntity<ProductFullDto> updateGoodByArticle(@RequestParam(value = "article") @Valid @PathVariable String name, @RequestBody @Valid ProductUpdateDto productUpdateDto) throws NotFoundByArticleException, SQLUniqueException {
        ProductFullDto good = productService.updateByArticle(productUpdateDto, name);
        return new ResponseEntity<>(good, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/goodsByArticle")
    public ResponseEntity<ProductFullDto> deleteGoodByArticle(@RequestParam(value = "article") @Valid @PathVariable String name) throws NotFoundByArticleException {
        productService.deleteByArticle(name);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
