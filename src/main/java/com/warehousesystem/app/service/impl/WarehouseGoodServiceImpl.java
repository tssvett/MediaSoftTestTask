package com.warehousesystem.app.service.impl;

import com.warehousesystem.app.handler.Exception.EmptyGoodsException;
import com.warehousesystem.app.handler.Exception.NotFoundByIdException;
import com.warehousesystem.app.handler.Exception.NotFoundByArticleException;
import com.warehousesystem.app.handler.Exception.SQLUniqueException;
import com.warehousesystem.app.model.WarehouseGood;
import com.warehousesystem.app.repository.WarehouseGoodRepository;
import com.warehousesystem.app.service.WarehouseGoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class WarehouseGoodServiceImpl implements WarehouseGoodService {

    @Autowired
    private WarehouseGoodRepository warehouseGoodRepository;

    @Override
    public WarehouseGood create(WarehouseGood warehouseGood) throws SQLUniqueException {
        try {
             return warehouseGoodRepository.save(warehouseGood);
        } catch (Exception e) {
            throw new SQLUniqueException(e.getMessage());
        }

    }



    @Override
    public WarehouseGood readById(UUID id) throws NotFoundByIdException {
        if (!warehouseGoodRepository.existsById(id)) {
            throw new NotFoundByIdException();
        }
        return warehouseGoodRepository.getReferenceById(id);
    }

    @Override
    public WarehouseGood readByArticle(String article) throws NotFoundByArticleException {
        if (!warehouseGoodRepository.existsByArticle(article)) {
            throw new NotFoundByArticleException();
        }

        return warehouseGoodRepository.getReferenceByArticle(article);
    }

    @Override
    public List<WarehouseGood> readALl() throws EmptyGoodsException {
        List<WarehouseGood> goods = warehouseGoodRepository.findAll();
        if (goods.isEmpty()) {
            throw new EmptyGoodsException();
        }
        return goods;
    }

    @Override
    public WarehouseGood updateById(WarehouseGood warehouseGood, UUID id) throws NotFoundByIdException, SQLUniqueException {
        if (!warehouseGoodRepository.existsById(id)) {
            throw new NotFoundByIdException();
        }
        warehouseGood.setId(id);
        try {
            warehouseGoodRepository.save(warehouseGood);
            return warehouseGood;
        }
        catch (Exception e) {
            throw new SQLUniqueException(e.getMessage());
        }
    }

    @Override
    public WarehouseGood updateByArticle(WarehouseGood warehouseGood, String article) throws NotFoundByArticleException, SQLUniqueException {
        if (!warehouseGoodRepository.existsByArticle(article)) {
            throw new NotFoundByArticleException();
        }
        WarehouseGood warehouseGood1 = warehouseGoodRepository.getReferenceByArticle(article);
        warehouseGood1.setName(warehouseGood.getName());
        try {
            warehouseGoodRepository.save(warehouseGood1);
            return warehouseGood1;
        }
        catch (Exception e) {
            throw new SQLUniqueException(e.getMessage());
        }
    }

    @Override
    public void deleteById(UUID id) throws NotFoundByIdException {
        if (!warehouseGoodRepository.existsById(id)) {
            throw new NotFoundByIdException();
        }
        warehouseGoodRepository.deleteById(id);
    }

    @Override
    public void deleteByArticle(String article) throws NotFoundByArticleException {
        if (!warehouseGoodRepository.existsByArticle(article)) {
            throw new NotFoundByArticleException();
        }
        warehouseGoodRepository.deleteByArticle(article);
    }

    @Override
    public void deleteAll() throws EmptyGoodsException {
        List<WarehouseGood> goods = warehouseGoodRepository.findAll();
        if (goods.isEmpty()) {
            throw new EmptyGoodsException();
        }
        warehouseGoodRepository.deleteAll();
    }
}
