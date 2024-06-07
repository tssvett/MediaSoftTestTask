package com.warehousesystem.app.service.impl;

import com.warehousesystem.app.dto.WarehouseGoodFullDto;
import com.warehousesystem.app.dto.WarehouseGoodSearchDto;
import com.warehousesystem.app.dto.WarehouseGoodUpdateDto;
import com.warehousesystem.app.handler.Exception.EmptyGoodsException;
import com.warehousesystem.app.handler.Exception.NotFoundByArticleException;
import com.warehousesystem.app.handler.Exception.NotFoundByIdException;
import com.warehousesystem.app.handler.Exception.SQLUniqueException;
import com.warehousesystem.app.model.WarehouseGood;
import com.warehousesystem.app.repository.WarehouseGoodRepository;
import com.warehousesystem.app.search.criteria.SearchCriteria;
import com.warehousesystem.app.search.specification.WarehouseGoodSpecification;
import com.warehousesystem.app.service.WarehouseGoodService;
import com.warehousesystem.app.utils.MappingUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WarehouseGoodServiceImpl implements WarehouseGoodService {

    private final WarehouseGoodRepository warehouseGoodRepository;
    private final MappingUtils mappingUtils;

    @Override
    public WarehouseGoodFullDto create(WarehouseGoodUpdateDto warehouseGoodUpdateDto) throws SQLUniqueException {
        try {
            WarehouseGood warehouseGood = mappingUtils.mapUpdateToWarehouseGood(warehouseGoodUpdateDto);
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
    public List<WarehouseGoodFullDto> readAll(WarehouseGoodSearchDto warehouseGoodSearchDto) throws EmptyGoodsException {
        int size = warehouseGoodSearchDto.getSize();
        int pageNumber = warehouseGoodSearchDto.getPageNumber();
        PageRequest request = PageRequest.of(pageNumber, size, Sort.by(Sort.Direction.ASC, "price"));
        List<WarehouseGoodFullDto> goods = warehouseGoodRepository.findAll(request)
                .stream()
                .map(mappingUtils::mapToWarehouseGoodFullDto)
                .collect(Collectors.toList());

        if (goods.isEmpty()) {
            throw new EmptyGoodsException();
        }

        return goods;
    }

    @Override
    public WarehouseGoodFullDto updateById(WarehouseGoodUpdateDto warehouseGoodUpdateDto, UUID id) throws NotFoundByIdException, SQLUniqueException {
        if (!warehouseGoodRepository.existsById(id)) {
            throw new NotFoundByIdException();
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
            throw new NotFoundByArticleException();
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

    @Override
    public List<WarehouseGoodFullDto> readSortedGoods(List<SearchCriteria> criteriaList, Pageable pageable) throws Exception, EmptyGoodsException {
        WarehouseGoodSpecification specification = new WarehouseGoodSpecification(criteriaList);
        Page<WarehouseGood> goods = warehouseGoodRepository.findAll(specification.createSpecification(), pageable);

        return goods.getContent().stream().map(mappingUtils::mapToWarehouseGoodFullDto).collect(Collectors.toList());
    }
}
