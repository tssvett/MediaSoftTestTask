package com.warehousesystem.app.controller;

import com.warehousesystem.app.dto.WarehouseGoodFullDto;
import com.warehousesystem.app.dto.WarehouseGoodUpdateDto;
import com.warehousesystem.app.handler.Exception.EmptyGoodsException;
import com.warehousesystem.app.handler.Exception.NotFoundByIdException;
import com.warehousesystem.app.handler.Exception.NotFoundByArticleException;
import com.warehousesystem.app.handler.Exception.SQLUniqueException;
import com.warehousesystem.app.service.WarehouseGoodService;
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
public class WarehouseController {

    @Autowired
    public WarehouseGoodService warehouseGoodService;

    @GetMapping("/goods")
    public ResponseEntity<List<WarehouseGoodFullDto>> getGoodsAll() throws EmptyGoodsException {
        final List<WarehouseGoodFullDto> goods = warehouseGoodService.readAll();
        return new ResponseEntity<>(goods, HttpStatus.OK);
    }

    @PostMapping("/goods")
    public ResponseEntity<WarehouseGoodFullDto> createGood(@Valid @RequestBody WarehouseGoodFullDto warehouseGood) throws SQLUniqueException {
        WarehouseGoodFullDto good = warehouseGoodService.create(warehouseGood);
        return new ResponseEntity<>(good, HttpStatus.CREATED);
    }

    @DeleteMapping("/goods")
    public ResponseEntity<WarehouseGoodFullDto> deleteAllGoods() throws EmptyGoodsException, NotFoundByArticleException {
        warehouseGoodService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/goods/{id}")
    public ResponseEntity<WarehouseGoodFullDto> getGoodById(@Valid @PathVariable UUID id) throws NotFoundByIdException {
        final WarehouseGoodFullDto good = warehouseGoodService.readById(id);
        return new ResponseEntity<>(good, HttpStatus.OK);
    }

    @Transactional
    @PutMapping("/goods/{id}")
    public ResponseEntity<WarehouseGoodUpdateDto> updateGoodById(@PathVariable UUID id,
                                                                     @RequestBody WarehouseGoodUpdateDto good) throws NotFoundByIdException, SQLUniqueException {
        WarehouseGoodUpdateDto newGood = warehouseGoodService.updateById(good, id);
        return new ResponseEntity<>(newGood, HttpStatus.OK);
    }


    @Transactional
    @DeleteMapping("/goods/{id}")
    public ResponseEntity<WarehouseGoodFullDto> deleteGoodById(@PathVariable UUID id) throws NotFoundByIdException {
        warehouseGoodService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/goodsBy")
    public ResponseEntity<WarehouseGoodFullDto> getGoodByArticle(@RequestParam(value = "article" )@Valid @PathVariable String article) throws NotFoundByArticleException {
        final WarehouseGoodFullDto good = warehouseGoodService.readByArticle(article);
        return new ResponseEntity<>(good, HttpStatus.OK);
    }



    @Transactional
    @PutMapping("/goodsBy")
    public ResponseEntity<WarehouseGoodUpdateDto> updateGoodByArticle(@RequestParam(value = "article" ) @Valid @PathVariable String name, @RequestBody WarehouseGoodUpdateDto goodBody) throws NotFoundByArticleException, SQLUniqueException {
        WarehouseGoodUpdateDto good = warehouseGoodService.updateByArticle(goodBody, name);
        return new ResponseEntity<>(good, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/goodsBy")
    public ResponseEntity<WarehouseGoodFullDto> deleteGoodByArticle(@RequestParam(value = "article" ) @Valid @PathVariable String name) throws NotFoundByArticleException {
        warehouseGoodService.deleteByArticle(name);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
