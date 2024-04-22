package com.warehousesystem.app.controller;

import com.warehousesystem.app.dto.WarehouseGoodFullDto;
import com.warehousesystem.app.dto.WarehouseGoodSearchDto;
import com.warehousesystem.app.dto.WarehouseGoodUpdateDto;
import com.warehousesystem.app.handler.exception.EmptyGoodsException;
import com.warehousesystem.app.handler.exception.NotFoundByArticleException;
import com.warehousesystem.app.handler.exception.NotFoundByIdException;
import com.warehousesystem.app.handler.exception.SQLUniqueException;
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
    public ResponseEntity<List<WarehouseGoodFullDto>> getGoodsAll(@Valid @RequestBody WarehouseGoodSearchDto warehouseGoodSearchDto) throws EmptyGoodsException {
        final List<WarehouseGoodFullDto> goods = warehouseGoodService.readAll(warehouseGoodSearchDto);
        return new ResponseEntity<>(goods, HttpStatus.OK);
    }

    @PostMapping("/goods")
    public ResponseEntity<WarehouseGoodFullDto> createGood(@Valid @RequestBody WarehouseGoodUpdateDto warehouseGood) throws SQLUniqueException {
        WarehouseGoodFullDto good = warehouseGoodService.create(warehouseGood);
        return new ResponseEntity<>(good, HttpStatus.CREATED);
    }

    @DeleteMapping("/goods")
    public ResponseEntity<WarehouseGoodFullDto> deleteAllGoods() throws EmptyGoodsException, NotFoundByArticleException {
        warehouseGoodService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/goodsById")
    public ResponseEntity<WarehouseGoodFullDto> getGoodById(@RequestParam(value = "id") @Valid @PathVariable UUID id) throws NotFoundByIdException {
        final WarehouseGoodFullDto good = warehouseGoodService.readById(id);
        return new ResponseEntity<>(good, HttpStatus.OK);
    }

    @Transactional
    @PutMapping("/goodsById")
    public ResponseEntity<WarehouseGoodFullDto> updateGoodById(@RequestParam(value = "id") @Valid @PathVariable UUID id,
                                                               @Valid @RequestBody WarehouseGoodUpdateDto good) throws NotFoundByIdException, SQLUniqueException {
        WarehouseGoodFullDto newGood = warehouseGoodService.updateById(good, id);
        return new ResponseEntity<>(newGood, HttpStatus.OK);
    }


    @Transactional
    @DeleteMapping("/goodsById")
    public ResponseEntity<WarehouseGoodFullDto> deleteGoodById(@RequestParam(value = "id") @Valid @PathVariable UUID id) throws NotFoundByIdException {
        warehouseGoodService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/goodsByArticle")
    public ResponseEntity<WarehouseGoodFullDto> getGoodByArticle(@RequestParam(value = "article") @Valid @PathVariable String article) throws NotFoundByArticleException {
        final WarehouseGoodFullDto good = warehouseGoodService.readByArticle(article);
        return new ResponseEntity<>(good, HttpStatus.OK);
    }


    @Transactional
    @PutMapping("/goodsByArticle")
    public ResponseEntity<WarehouseGoodFullDto> updateGoodByArticle(@RequestParam(value = "article") @Valid @PathVariable String name, @RequestBody @Valid WarehouseGoodUpdateDto goodBody) throws NotFoundByArticleException, SQLUniqueException {
        WarehouseGoodFullDto good = warehouseGoodService.updateByArticle(goodBody, name);
        return new ResponseEntity<>(good, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/goodsByArticle")
    public ResponseEntity<WarehouseGoodFullDto> deleteGoodByArticle(@RequestParam(value = "article") @Valid @PathVariable String name) throws NotFoundByArticleException {
        warehouseGoodService.deleteByArticle(name);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
