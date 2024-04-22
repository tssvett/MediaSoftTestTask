package com.warehousesystem.app.service;

import com.warehousesystem.app.dto.WarehouseGoodFullDto;
import com.warehousesystem.app.dto.WarehouseGoodSearchDto;
import com.warehousesystem.app.dto.WarehouseGoodUpdateDto;
import com.warehousesystem.app.handler.exception.EmptyGoodsException;
import com.warehousesystem.app.handler.exception.NotFoundByArticleException;
import com.warehousesystem.app.handler.exception.NotFoundByIdException;
import com.warehousesystem.app.handler.exception.SQLUniqueException;

import java.util.List;
import java.util.UUID;

public interface WarehouseGoodService {

    /**
     * Создает новый товар на складе
     *
     * @param warehouseGood - товар для создания
     */
    WarehouseGoodFullDto create(WarehouseGoodUpdateDto warehouseGood) throws SQLUniqueException;


    /**
     * Возвращает товар на складе по его уникальному артикулу
     *
     * @param id
     * @return - объект товара с заданным артикулом
     */
    WarehouseGoodFullDto readById(UUID id) throws NotFoundByIdException;

    /**
     * Возвращает товар на складе по его уникальному имени
     *
     * @param article
     * @return - список товаров с заданным именем
     */
    WarehouseGoodFullDto readByArticle(String article) throws NotFoundByArticleException;

    /**
     * Обновляет товар на складе по заданному артикулу
     * @param warehouseGood
     * @param id
     */

    /**
     * Возвращает список всех имеющихся товаров на складе
     *
     * @return - список
     */
    List<WarehouseGoodFullDto> readAll(WarehouseGoodSearchDto warehouseGoodSearchDto) throws EmptyGoodsException;

    WarehouseGoodFullDto updateById(WarehouseGoodUpdateDto warehouseGood, UUID id) throws NotFoundByIdException, SQLUniqueException;

    /**
     * Обновляет товар на складе по заданному имени
     *
     * @param warehouseGood
     * @param article
     */
    WarehouseGoodFullDto updateByArticle(WarehouseGoodUpdateDto warehouseGood, String article) throws NotFoundByArticleException, SQLUniqueException;

    /**
     * Удаляет товар на складе по заданному артикулу
     *
     * @param id
     */
    void deleteById(UUID id) throws NotFoundByIdException;

    /**
     * Удаляет товар на складе по заданному артикулу
     *
     * @param name
     */
    void deleteByArticle(String name) throws NotFoundByArticleException;

    /**
     * Удаляет все....
     */
    void deleteAll() throws NotFoundByArticleException, EmptyGoodsException;

}
