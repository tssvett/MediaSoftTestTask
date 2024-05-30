package com.warehousesystem.app.service;

import com.warehousesystem.app.dto.product.ProductCreateDto;
import com.warehousesystem.app.dto.product.ProductFullDto;
import com.warehousesystem.app.dto.product.ProductSearchDto;
import com.warehousesystem.app.dto.product.ProductUpdateDto;
import com.warehousesystem.app.errorhandler.Exception.EmptyProductException;
import com.warehousesystem.app.errorhandler.Exception.NotFoundByArticleException;
import com.warehousesystem.app.errorhandler.Exception.SQLUniqueException;
import com.warehousesystem.app.errorhandler.Exception.NotFoundByIdException;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    /**
     * Создает новый товар на складе
     * @param warehouseGood - товар для создания
     */
    ProductFullDto create(ProductCreateDto warehouseGood) throws SQLUniqueException;


    /**
     * Возвращает товар на складе по его уникальному артикулу
     * @param id
     * @return - объект товара с заданным артикулом
     */
    ProductFullDto readById(UUID id) throws NotFoundByIdException;

    /**
     * Возвращает товар на складе по его уникальному имени
     * @param article
     * @return - список товаров с заданным именем
     */
    ProductFullDto readByArticle(String article) throws NotFoundByArticleException;

    /**
     * Обновляет товар на складе по заданному артикулу
     * @param warehouseGood
     * @param id
     */

    /**
     * Возвращает список всех имеющихся товаров на складе
     * @return - список
     */
    List<ProductFullDto> readAll(ProductSearchDto warehouseGoodSearchDto) throws EmptyProductException;
    ProductFullDto updateById(ProductUpdateDto warehouseGood, UUID id) throws NotFoundByIdException, SQLUniqueException;

    /**
     * Обновляет товар на складе по заданному имени
     * @param warehouseGood
     * @param article
     */
    ProductFullDto updateByArticle(ProductUpdateDto warehouseGood, String article) throws NotFoundByArticleException, SQLUniqueException;

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
    void deleteAll() throws NotFoundByArticleException, EmptyProductException;

}
