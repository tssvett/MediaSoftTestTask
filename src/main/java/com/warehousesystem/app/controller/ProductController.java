package com.warehousesystem.app.controller;

import com.warehousesystem.app.dto.product.ProductCreateDto;
import com.warehousesystem.app.dto.product.ProductFullDto;
import com.warehousesystem.app.dto.product.ProductSearchDto;
import com.warehousesystem.app.dto.product.ProductUpdateDto;
import com.warehousesystem.app.handler.Exception.EmptyProductException;
import com.warehousesystem.app.handler.Exception.NotFoundByArticleException;
import com.warehousesystem.app.handler.Exception.NotFoundByIdException;
import com.warehousesystem.app.handler.Exception.SQLUniqueException;
import com.warehousesystem.app.service.ProductService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Validated
@CrossOrigin
@RequestMapping("/warehouse")
public class ProductController {

    @Autowired
    public ProductService warehouseGoodService;

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
}
