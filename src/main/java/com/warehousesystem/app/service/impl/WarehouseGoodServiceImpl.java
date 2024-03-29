package com.warehousesystem.app.service.impl;

import com.warehousesystem.app.dto.WarehouseGoodFullDto;
import com.warehousesystem.app.dto.WarehouseGoodUpdateDto;
import com.warehousesystem.app.handler.Exception.EmptyGoodsException;
import com.warehousesystem.app.handler.Exception.NotFoundByIdException;
import com.warehousesystem.app.handler.Exception.NotFoundByArticleException;
import com.warehousesystem.app.handler.Exception.SQLUniqueException;
import com.warehousesystem.app.model.WarehouseGood;
import com.warehousesystem.app.repository.WarehouseGoodRepository;
import com.warehousesystem.app.service.WarehouseGoodService;
import com.warehousesystem.app.utils.MappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WarehouseGoodServiceImpl implements WarehouseGoodService {

    @Autowired
    private WarehouseGoodRepository warehouseGoodRepository;

    @Autowired
    private MappingUtils mappingUtils;

    @Override
    public WarehouseGoodFullDto create(WarehouseGoodFullDto warehouseGoodFullDto) throws SQLUniqueException {
        try {
            WarehouseGood warehouseGood = mappingUtils.mapFullToWarehouseGood(warehouseGoodFullDto);
            return mappingUtils.mapToWarehouseGoodFullDto(warehouseGoodRepository.save(warehouseGood));
        } catch (Exception e) {
            throw new SQLUniqueException(e.getMessage());
        }

    }



    @Override
    public WarehouseGoodFullDto readById(UUID id) throws NotFoundByIdException {
        if (!warehouseGoodRepository.existsById(id)) {
            throw new NotFoundByIdException();
        }
        return mappingUtils.mapToWarehouseGoodFullDto(warehouseGoodRepository.getReferenceById(id));
    }

    @Override
    public WarehouseGoodFullDto readByArticle(String article) throws NotFoundByArticleException {
        if (!warehouseGoodRepository.existsByArticle(article)) {
            throw new NotFoundByArticleException();
        }

        return mappingUtils.mapToWarehouseGoodFullDto(warehouseGoodRepository.getReferenceByArticle(article));
    }

    @Override
    public List<WarehouseGoodFullDto> readAll() throws EmptyGoodsException {
        List<WarehouseGoodFullDto> goods = warehouseGoodRepository.findAll().stream().map(mappingUtils::mapToWarehouseGoodFullDto).collect(Collectors.toList());
        if (goods.isEmpty()) {
            throw new EmptyGoodsException();
        }
        return goods;
    }

    @Override
    public WarehouseGoodUpdateDto updateById(WarehouseGoodUpdateDto warehouseGoodUpdateDto, UUID id) throws NotFoundByIdException, SQLUniqueException {
        if (!warehouseGoodRepository.existsById(id)) {
            throw new NotFoundByIdException();
        }
        WarehouseGood warehouseGood = mappingUtils.mapUpdateToWarehouseGood(warehouseGoodUpdateDto);
        warehouseGood.setId(id);
        try {
            warehouseGoodRepository.save(warehouseGood);
            return mappingUtils.mapToWarehouseGoodUpdateDto(warehouseGood);
        }
        catch (Exception e) {
            throw new SQLUniqueException(e.getMessage());
        }
    }

    @Override
    public WarehouseGoodUpdateDto updateByArticle(WarehouseGoodUpdateDto warehouseGood, String article) throws NotFoundByArticleException, SQLUniqueException {
        if (!warehouseGoodRepository.existsByArticle(article)) {
            throw new NotFoundByArticleException();
        }
        WarehouseGood warehouseGood1 = warehouseGoodRepository.getReferenceByArticle(article);

        warehouseGood1.setName(warehouseGood.getName());
        try {
            warehouseGoodRepository.save(warehouseGood1);
            return mappingUtils.mapToWarehouseGoodUpdateDto(warehouseGood1);
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
