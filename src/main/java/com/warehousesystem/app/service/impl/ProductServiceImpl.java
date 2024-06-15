package com.warehousesystem.app.service.impl;

import com.warehousesystem.app.dto.ProductFullDto;
import com.warehousesystem.app.dto.product.ProductCreateDto;
import com.warehousesystem.app.dto.product.ProductSearchDto;
import com.warehousesystem.app.dto.product.ProductUpdateDto;
import com.warehousesystem.app.handler.exception.EmptyProductException;
import com.warehousesystem.app.handler.exception.NotFoundByArticleException;
import com.warehousesystem.app.handler.exception.NotFoundByIdException;
import com.warehousesystem.app.handler.exception.SQLUniqueException;
import com.warehousesystem.app.model.Product;
import com.warehousesystem.app.repository.ProductRepository;
import com.warehousesystem.app.search.criteria.SearchCriteria;
import com.warehousesystem.app.search.specification.ProductSpecification;
import com.warehousesystem.app.service.ProductService;
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
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final MappingUtils mappingUtils;

    @Override
    public ProductFullDto create(ProductCreateDto productCreateDto) {
        try {
            Product warehouseGood = mappingUtils.mapUpdateToWarehouseGood(productCreateDto);

            return mappingUtils.mapToWarehouseGoodFullDto(productRepository.save(warehouseGood));
        } catch (Exception e) {
            throw new SQLUniqueException(e.getMessage());
        }
    }


    @Override
    public ProductFullDto readById(UUID id) {
        if (!productRepository.existsById(id)) {
            throw new NotFoundByIdException();
        }

        return mappingUtils.mapToWarehouseGoodFullDto(productRepository.getReferenceById(id));
    }

    @Override
    public ProductFullDto readByArticle(String article) {
        if (!productRepository.existsByArticle(article)) {
            throw new NotFoundByArticleException();
        }

        return mappingUtils.mapToWarehouseGoodFullDto(productRepository.getReferenceByArticle(article));
    }

    @Override
    public List<ProductFullDto> readAll(ProductSearchDto warehouseGoodSearchDto) {
        int size = warehouseGoodSearchDto.getSize();
        int pageNumber = warehouseGoodSearchDto.getPageNumber();
        PageRequest request = PageRequest.of(pageNumber, size, Sort.by(Sort.Direction.ASC, "price"));
        List<ProductFullDto> goods = productRepository.findAll(request)
                .stream()
                .map(mappingUtils::mapToWarehouseGoodFullDto)
                .collect(Collectors.toList());
        if (goods.isEmpty()) {
            throw new EmptyProductException();
        }

        return goods;
    }

    @Override
    public ProductFullDto updateById(ProductUpdateDto warehouseGoodUpdateDto, UUID id) {
        if (!productRepository.existsById(id)) {
            throw new NotFoundByIdException();
        }
        Product warehouseGood1 = productRepository.getReferenceById(id);
        try {
            if (productRepository.existsByArticle(warehouseGoodUpdateDto.getArticle()) && !warehouseGood1.getArticle().equals(warehouseGoodUpdateDto.getArticle())) {
                throw new SQLUniqueException("Article already exist");
            }
            warehouseGood1.setArticle(warehouseGoodUpdateDto.getArticle());
            warehouseGood1.setName(warehouseGoodUpdateDto.getName());
            warehouseGood1.setPrice(warehouseGoodUpdateDto.getPrice());
            warehouseGood1.setQuantity(warehouseGoodUpdateDto.getQuantity());
            warehouseGood1.setCategory(warehouseGoodUpdateDto.getCategory());
            warehouseGood1.setDescription(warehouseGoodUpdateDto.getDescription());
            productRepository.save(warehouseGood1);

            return mappingUtils.mapToWarehouseGoodFullDto(warehouseGood1);
        } catch (Exception e) {
            throw new SQLUniqueException(e.getMessage());
        }
    }


    @Override
    public ProductFullDto updateByArticle(ProductUpdateDto warehouseGoodUpdateDto, String article) {
        if (!productRepository.existsByArticle(article)) {
            throw new NotFoundByArticleException();
        }
        Product foundedGood = productRepository.getReferenceByArticle(article);
        try {
            if (productRepository.existsByArticle(warehouseGoodUpdateDto.getArticle()) && !warehouseGoodUpdateDto.getArticle().equals(article)) {
                throw new SQLUniqueException("Article already exist");
            }
            foundedGood.setArticle(warehouseGoodUpdateDto.getArticle());
            foundedGood.setName(warehouseGoodUpdateDto.getName());
            foundedGood.setPrice(warehouseGoodUpdateDto.getPrice());
            foundedGood.setQuantity(warehouseGoodUpdateDto.getQuantity());
            foundedGood.setCategory(warehouseGoodUpdateDto.getCategory());
            foundedGood.setDescription(warehouseGoodUpdateDto.getDescription());
            productRepository.save(foundedGood);

            return mappingUtils.mapToWarehouseGoodFullDto(foundedGood);
        } catch (Exception e) {
            throw new SQLUniqueException(e.getMessage());
        }
    }

    @Override
    public void deleteById(UUID id) {
        if (!productRepository.existsById(id)) {
            throw new NotFoundByIdException();
        }
        productRepository.deleteById(id);
    }

    @Override
    public void deleteByArticle(String article) {
        if (!productRepository.existsByArticle(article)) {
            throw new NotFoundByArticleException();
        }
        productRepository.deleteByArticle(article);
    }

    @Override
    public void deleteAll() throws EmptyProductException {
        List<Product> goods = productRepository.findAll();
        if (goods.isEmpty()) {
            throw new EmptyProductException();
        }
        productRepository.deleteAll();
    }

    @Override
    public List<ProductFullDto> readSortedGoods(List<SearchCriteria> criteriaList, Pageable pageable) {
        ProductSpecification specification = new ProductSpecification(criteriaList);
        Page<Product> goods = productRepository.findAll(specification.createSpecification(), pageable);

        return goods.getContent().stream().map(mappingUtils::mapToWarehouseGoodFullDto).collect(Collectors.toList());
    }
}
