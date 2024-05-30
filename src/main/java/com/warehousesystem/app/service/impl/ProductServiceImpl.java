package com.warehousesystem.app.service.impl;

import com.warehousesystem.app.dto.product.ProductCreateDto;
import com.warehousesystem.app.dto.product.ProductFullDto;
import com.warehousesystem.app.dto.product.ProductSearchDto;
import com.warehousesystem.app.dto.product.ProductUpdateDto;
import com.warehousesystem.app.errorhandler.Exception.EmptyProductException;
import com.warehousesystem.app.errorhandler.Exception.NotFoundByArticleException;
import com.warehousesystem.app.errorhandler.Exception.NotFoundByIdException;
import com.warehousesystem.app.errorhandler.Exception.SQLUniqueException;
import com.warehousesystem.app.model.Product;
import com.warehousesystem.app.repository.ProductRepository;
import com.warehousesystem.app.service.ProductService;
import com.warehousesystem.app.utils.MappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository warehouseGoodRepository;

    @Autowired
    private MappingUtils mappingUtils;

    @Override
    public ProductFullDto create(ProductCreateDto warehouseGoodCreateDto) throws SQLUniqueException {
        try {
            Product warehouseGood = mappingUtils.mapUpdateToWarehouseGood(warehouseGoodCreateDto);
            return mappingUtils.mapToWarehouseGoodFullDto(warehouseGoodRepository.save(warehouseGood));
        } catch (Exception e) {
            throw new SQLUniqueException(e.getMessage());
        }
    }


    @Override
    public ProductFullDto readById(UUID id) throws NotFoundByIdException {
        if (!warehouseGoodRepository.existsById(id)) {
            throw new NotFoundByIdException("No Warehouse good with id: " + id);
        }
        return mappingUtils.mapToWarehouseGoodFullDto(warehouseGoodRepository.getReferenceById(id));
    }

    @Override
    public ProductFullDto readByArticle(String article) throws NotFoundByArticleException {
        if (!warehouseGoodRepository.existsByArticle(article)) {
            throw new NotFoundByArticleException("No Warehouse good with article: " + article);
        }

        return mappingUtils.mapToWarehouseGoodFullDto(warehouseGoodRepository.getReferenceByArticle(article));
    }

    @Override
    public List<ProductFullDto> readAll(ProductSearchDto warehouseGoodSearchDto) throws EmptyProductException {
        int size = warehouseGoodSearchDto.getSize();
        int pageNumber = warehouseGoodSearchDto.getPageNumber();

        // Вычисляем смещение для пагинации
        int offset = pageNumber * size;
        PageRequest request = PageRequest.of(pageNumber, size, Sort.by(Sort.Direction.ASC, "price"));
        List<ProductFullDto> goods = warehouseGoodRepository.findAll(request)
                .stream()
                .map(mappingUtils::mapToWarehouseGoodFullDto)
                .collect(Collectors.toList());

        if (goods.isEmpty()) {
            throw new EmptyProductException("Warehouse is empty");
        }

        return goods;
    }

    @Override
    public ProductFullDto updateById(ProductUpdateDto warehouseGoodUpdateDto, UUID id) throws NotFoundByIdException, SQLUniqueException {
        if (!warehouseGoodRepository.existsById(id)) {
            throw new NotFoundByIdException("No Warehouse good with id: " + id);
        }
        Product warehouseGood1 = warehouseGoodRepository.getReferenceById(id);
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
    public ProductFullDto updateByArticle(ProductUpdateDto warehouseGoodUpdateDto, String article) throws NotFoundByArticleException, SQLUniqueException {
        if (!warehouseGoodRepository.existsByArticle(article)) {
            throw new NotFoundByArticleException("No Warehouse good with article: " + article);
        }
        Product foundedGood = warehouseGoodRepository.getReferenceByArticle(article);
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
    public void deleteAll() throws EmptyProductException {
        List<Product> goods = warehouseGoodRepository.findAll();
        if (goods.isEmpty()) {
            throw new EmptyProductException("Warehouse is empty");
        }
        warehouseGoodRepository.deleteAll();
    }
}
