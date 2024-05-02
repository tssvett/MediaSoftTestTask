package com.warehousesystem.app.service.impl;

import com.warehousesystem.app.currency.CurrencyRateProvider;
import com.warehousesystem.app.dto.WarehouseGoodCreateDto;
import com.warehousesystem.app.dto.WarehouseGoodFullDto;
import com.warehousesystem.app.dto.WarehouseGoodSearchDto;
import com.warehousesystem.app.dto.WarehouseGoodUpdateDto;
import com.warehousesystem.app.handler.Exception.EmptyGoodsException;
import com.warehousesystem.app.handler.Exception.NotFoundByArticleException;
import com.warehousesystem.app.handler.Exception.NotFoundByIdException;
import com.warehousesystem.app.handler.Exception.SQLUniqueException;
import com.warehousesystem.app.entity.WarehouseGood;
import com.warehousesystem.app.repository.WarehouseGoodRepository;
import com.warehousesystem.app.service.WarehouseGoodService;
import com.warehousesystem.app.utils.MappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WarehouseGoodServiceImpl implements WarehouseGoodService {

    @Autowired
    private WarehouseGoodRepository warehouseGoodRepository;

    @Autowired
    private CurrencyRateProvider currencyRateProvider;

    @Autowired
    private MappingUtils mappingUtils;

    @Override
    public WarehouseGoodFullDto create(WarehouseGoodCreateDto warehouseGoodCreateDto) throws SQLUniqueException {
        try {
            WarehouseGood warehouseGood = mappingUtils.mapUpdateToWarehouseGood(warehouseGoodCreateDto);
            return mappingUtils.mapToWarehouseGoodFullDto(warehouseGoodRepository.save(warehouseGood));
        } catch (Exception e) {
            throw new SQLUniqueException(e.getMessage());
        }
    }


    @Override
    public WarehouseGoodFullDto readById(UUID id) throws NotFoundByIdException {
        if (!warehouseGoodRepository.existsById(id)) {
            throw new NotFoundByIdException("No Warehouse good with id: " + id);
        }
        WarehouseGoodFullDto warehouseGoodFullDto = mappingUtils.mapToWarehouseGoodFullDto(warehouseGoodRepository.getReferenceById(id));
        warehouseGoodFullDto.setPrice(warehouseGoodFullDto.getPrice().multiply(currencyRateProvider.getCurrencyValue()).setScale(2, RoundingMode.HALF_UP));
        warehouseGoodFullDto.setCurrency(currencyRateProvider.getCurrencyType());
        return warehouseGoodFullDto;
    }

    @Override
    public WarehouseGoodFullDto readByArticle(String article) throws NotFoundByArticleException {
        if (!warehouseGoodRepository.existsByArticle(article)) {
            throw new NotFoundByArticleException("No Warehouse good with article: " + article);
        }
        WarehouseGoodFullDto warehouseGoodFullDto = mappingUtils.mapToWarehouseGoodFullDto(warehouseGoodRepository.getReferenceByArticle(article));
        warehouseGoodFullDto.setPrice(warehouseGoodFullDto.getPrice().multiply(currencyRateProvider.getCurrencyValue()).setScale(2, RoundingMode.HALF_UP));
        warehouseGoodFullDto.setCurrency(currencyRateProvider.getCurrencyType());
        return warehouseGoodFullDto;
    }

    @Override
    public List<WarehouseGoodFullDto> readAll(WarehouseGoodSearchDto warehouseGoodSearchDto) throws EmptyGoodsException {
        int size = warehouseGoodSearchDto.getSize();
        int pageNumber = warehouseGoodSearchDto.getPageNumber();
        PageRequest request = PageRequest.of(pageNumber, size, Sort.by(Sort.Direction.ASC, "price"));

        BigDecimal currencyRate = currencyRateProvider.getCurrencyValue();
        List<WarehouseGoodFullDto> goods = warehouseGoodRepository.findAll(request)
                .stream()
                .map(mappingUtils::mapToWarehouseGoodFullDto)
                .collect(Collectors.toList());
        goods.forEach(good -> {
            good.setPrice(good.getPrice().multiply(currencyRate).setScale(2, RoundingMode.HALF_UP));
            good.setCurrency(currencyRateProvider.getCurrencyType());
        });
        if (goods.isEmpty()) {
            throw new EmptyGoodsException("Warehouse is empty");
        }

        return goods;
    }

    @Override
    public WarehouseGoodFullDto updateById(WarehouseGoodUpdateDto warehouseGoodUpdateDto, UUID id) throws NotFoundByIdException, SQLUniqueException {
        if (!warehouseGoodRepository.existsById(id)) {
            throw new NotFoundByIdException("No Warehouse good with id: " + id);
        }
        WarehouseGood warehouseGood1 = warehouseGoodRepository.getReferenceById(id);
        try {
            if (warehouseGoodRepository.existsByArticle(warehouseGoodUpdateDto.getArticle()) && !warehouseGood1.getArticle().equals(warehouseGoodUpdateDto.getArticle())) {
                throw new SQLUniqueException("Article already exist");
            }
            warehouseGood1.setArticle(warehouseGoodUpdateDto.getArticle());
            warehouseGood1.setName(warehouseGoodUpdateDto.getName());
            warehouseGood1.setPrice(warehouseGoodUpdateDto.getPrice());
            warehouseGood1.setQuantity(warehouseGoodUpdateDto.getQuantity());
            warehouseGood1.setCategory(warehouseGoodUpdateDto.getCategory());
            warehouseGood1.setDescription(warehouseGoodUpdateDto.getDescription());
            warehouseGoodRepository.save(warehouseGood1);
            return mappingUtils.mapToWarehouseGoodFullDto(warehouseGood1);
        } catch (Exception e) {
            throw new SQLUniqueException(e.getMessage());
        }
    }


    @Override
    public WarehouseGoodFullDto updateByArticle(WarehouseGoodUpdateDto warehouseGoodUpdateDto, String article) throws NotFoundByArticleException, SQLUniqueException {
        if (!warehouseGoodRepository.existsByArticle(article)) {
            throw new NotFoundByArticleException("No Warehouse good with article: " + article);
        }
        WarehouseGood foundedGood = warehouseGoodRepository.getReferenceByArticle(article);
        try {
            if (warehouseGoodRepository.existsByArticle(warehouseGoodUpdateDto.getArticle()) && !warehouseGoodUpdateDto.getArticle().equals(article)) {
                throw new SQLUniqueException("Article already exist");
            }
            foundedGood.setArticle(warehouseGoodUpdateDto.getArticle());
            foundedGood.setName(warehouseGoodUpdateDto.getName());
            foundedGood.setPrice(warehouseGoodUpdateDto.getPrice());
            foundedGood.setQuantity(warehouseGoodUpdateDto.getQuantity());
            foundedGood.setCategory(warehouseGoodUpdateDto.getCategory());
            foundedGood.setDescription(warehouseGoodUpdateDto.getDescription());
            warehouseGoodRepository.save(foundedGood);
            return mappingUtils.mapToWarehouseGoodFullDto(foundedGood);
        } catch (Exception e) {
            throw new SQLUniqueException(e.getMessage());
        }
    }

    @Override
    public void deleteById(UUID id) throws NotFoundByIdException {
        if (!warehouseGoodRepository.existsById(id)) {
            throw new NotFoundByIdException("No Warehouse good with id: " + id);
        }
        warehouseGoodRepository.deleteById(id);
    }

    @Override
    public void deleteByArticle(String article) throws NotFoundByArticleException {
        if (!warehouseGoodRepository.existsByArticle(article)) {
            throw new NotFoundByArticleException("No Warehouse good with article: " + article);
        }
        warehouseGoodRepository.deleteByArticle(article);
    }

    @Override
    public void deleteAll() throws EmptyGoodsException {
        List<WarehouseGood> goods = warehouseGoodRepository.findAll();
        if (goods.isEmpty()) {
            throw new EmptyGoodsException("Warehouse is empty");
        }
        warehouseGoodRepository.deleteAll();
    }
}
