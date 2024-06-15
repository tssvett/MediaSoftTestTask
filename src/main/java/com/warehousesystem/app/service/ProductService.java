package com.warehousesystem.app.service;

import com.warehousesystem.app.dto.ProductFullDto;
import com.warehousesystem.app.dto.product.ProductCreateDto;
import com.warehousesystem.app.dto.product.ProductSearchDto;
import com.warehousesystem.app.dto.product.ProductUpdateDto;
import com.warehousesystem.app.search.criteria.SearchCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    /**
     * Создает новый товар на складе
     *
     * @param warehouseGood - товар для создания
     */
    ProductFullDto create(ProductCreateDto warehouseGood);


    /**
     * Возвращает товар на складе по его уникальному артикулу
     *
     * @param id
     * @return - объект товара с заданным артикулом
     */
    ProductFullDto readById(UUID id);

    /**
     * Возвращает товар на складе по его уникальному имени
     *
     * @param article
     * @return - список товаров с заданным именем
     */
    ProductFullDto readByArticle(String article);

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
    List<ProductFullDto> readAll(ProductSearchDto warehouseGoodSearchDto);

    ProductFullDto updateById(ProductUpdateDto warehouseGood, UUID id);

    /**
     * Обновляет товар на складе по заданному имени
     *
     * @param warehouseGood
     * @param article
     */
    ProductFullDto updateByArticle(ProductUpdateDto warehouseGood, String article);

    /**
     * Удаляет товар на складе по заданному артикулу
     *
     * @param id
     */
    void deleteById(UUID id);

    /**
     * Удаляет товар на складе по заданному артикулу
     *
     * @param name
     */
    void deleteByArticle(String name);

    /**
     * Удаляет все....
     */
    void deleteAll();

    List<ProductFullDto> readSortedGoods(List<SearchCriteria> criteriaList, Pageable criteria);
}
