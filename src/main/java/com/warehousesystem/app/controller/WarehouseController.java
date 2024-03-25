package com.warehousesystem.app.controller;

import com.warehousesystem.app.handler.Exception.EmptyGoodsException;
import com.warehousesystem.app.handler.Exception.NotFoundByIdException;
import com.warehousesystem.app.handler.Exception.NotFoundByArticleException;
import com.warehousesystem.app.handler.Exception.SQLUniqueException;
import com.warehousesystem.app.model.WarehouseGood;
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
    public ResponseEntity<List<WarehouseGood>> getGoodsAll() throws EmptyGoodsException {
        final List<WarehouseGood> goods = warehouseGoodService.readALl();
        return new ResponseEntity<>(goods, HttpStatus.OK);
    }

    @PostMapping("/goods")
    public ResponseEntity<WarehouseGood> createGood(@Valid @RequestBody WarehouseGood warehouseGood) throws SQLUniqueException {
        final  WarehouseGood good = warehouseGoodService.create(warehouseGood);
        return new ResponseEntity<>(good, HttpStatus.OK);
    }

    @DeleteMapping("/goods")
    public ResponseEntity<WarehouseGood> deleteAllGoods() throws EmptyGoodsException, NotFoundByArticleException {
        warehouseGoodService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/goods/{id}")
    public ResponseEntity<WarehouseGood> getGoodById(@Valid @PathVariable UUID id) throws NotFoundByIdException {
        final WarehouseGood good = warehouseGoodService.readById(id);
        return new ResponseEntity<>(good, HttpStatus.OK);
    }

    @PutMapping("/goods/{id}")
    public ResponseEntity<WarehouseGood> updateGoodById(@PathVariable UUID id,
                                                        @RequestBody WarehouseGood good) throws NotFoundByIdException, SQLUniqueException {
        WarehouseGood newGood = warehouseGoodService.updateById(good, id);
        return new ResponseEntity<>(newGood, HttpStatus.OK);
    }


    @DeleteMapping("/goods/{id}")
    public ResponseEntity<WarehouseGood> deleteGoodById(@PathVariable UUID id) throws NotFoundByIdException {
        warehouseGoodService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/goodsBy")
    public ResponseEntity<WarehouseGood> getGoodByArticle(@RequestParam(value = "article" )@Valid @PathVariable String article) throws NotFoundByArticleException {
        final WarehouseGood good = warehouseGoodService.readByArticle(article);
        return new ResponseEntity<>(good, HttpStatus.OK);
    }



    @PutMapping("/goodsBy")
    public ResponseEntity<WarehouseGood> updateGoodByArticle(@RequestParam(value = "article" ) @Valid @PathVariable String name, @RequestBody WarehouseGood goodBody) throws NotFoundByArticleException, SQLUniqueException {
        WarehouseGood good = warehouseGoodService.updateByArticle(goodBody, name);
        return new ResponseEntity<>(good, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/goodsBy")
    public ResponseEntity<WarehouseGood> deleteGoodByArticle(@RequestParam(value = "article" ) @Valid @PathVariable String name) throws NotFoundByArticleException {
        warehouseGoodService.deleteByArticle(name);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
