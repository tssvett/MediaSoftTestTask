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
import org.springframework.web.bind.annotation.ResponseStatus;
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
    @ResponseStatus(HttpStatus.OK)
    public List<ProductFullDto> getGoodsAll(@Valid @RequestBody ProductSearchDto productSearchDto) throws EmptyProductException {
        return productService.readAll(productSearchDto);
    }

    @PostMapping("/goods")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductFullDto createGood(@Valid @RequestBody ProductCreateDto productCreateDto) throws SQLUniqueException {
        return productService.create(productCreateDto);
    }

    @DeleteMapping("/goods")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAllGoods() throws EmptyProductException, NotFoundByArticleException {
        productService.deleteAll();
    }

    @GetMapping("/goodsById")
    @ResponseStatus(HttpStatus.OK)
    public ProductFullDto getGoodById(@RequestParam(value = "id") @Valid @PathVariable UUID id) throws NotFoundByIdException {
        return productService.readById(id);
    }

    @Transactional
    @PutMapping("/goodsById")
    @ResponseStatus(HttpStatus.OK)
    public ProductFullDto updateGoodById(@RequestParam(value = "id") @Valid @PathVariable UUID id,
                                         @Valid @RequestBody ProductUpdateDto productUpdateDto) throws NotFoundByIdException, SQLUniqueException {
        return productService.updateById(productUpdateDto, id);
    }


    @Transactional
    @DeleteMapping("/goodsById")
    @ResponseStatus(HttpStatus.OK)
    public void deleteGoodById(@RequestParam(value = "id") @Valid @PathVariable UUID id) throws NotFoundByIdException {
        productService.deleteById(id);
    }

    @GetMapping("/goodsByArticle")
    @ResponseStatus(HttpStatus.OK)
    public ProductFullDto getGoodByArticle(@RequestParam(value = "article") @Valid @PathVariable String article) throws NotFoundByArticleException {
        return productService.readByArticle(article);
    }

    @Transactional
    @PutMapping("/goodsByArticle")
    @ResponseStatus(HttpStatus.OK)
    public ProductFullDto updateGoodByArticle(@RequestParam(value = "article") @Valid @PathVariable String name, @RequestBody @Valid ProductUpdateDto productUpdateDto) throws NotFoundByArticleException, SQLUniqueException {
        return productService.updateByArticle(productUpdateDto, name);
    }

    @Transactional
    @DeleteMapping("/goodsByArticle")
    @ResponseStatus(HttpStatus.OK)
    public void deleteGoodByArticle(@RequestParam(value = "article") @Valid @PathVariable String name) throws NotFoundByArticleException {
        productService.deleteByArticle(name);
    }
}
