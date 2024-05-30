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
import com.warehousesystem.app.utils.MappingUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class WarehouseGoodImplTest {

    @Mock
    ProductRepository goodRepository;

    @Mock
    MappingUtils mappingUtils;

    @InjectMocks
    ProductServiceImpl goodService;

    static Product good1;
    static Product good2;
    static Product good3;
    static Product good4;
    static Product good5;

    static ProductCreateDto goodUpdateDto1;
    static ProductUpdateDto goodUpdateDto2;
    static ProductUpdateDto goodUpdateDto3;

    static ProductFullDto goodFullDto1;
    static ProductFullDto goodFullDto2;
    static ProductFullDto goodFullDto3;


    @BeforeAll
    static void setUp() {
        good1 = new Product();
        good2 = new Product();
        good3 = new Product();
        good4 = new Product();
        good5 = new Product();

        goodUpdateDto1 = new ProductCreateDto();
        goodUpdateDto2 = new ProductUpdateDto();
        goodUpdateDto3 = new ProductUpdateDto();

        goodFullDto1 = new ProductFullDto();
        goodFullDto2 = new ProductFullDto();
        goodFullDto3 = new ProductFullDto();
        //given
        good1.setName("Хороший товар");
        good1.setId(UUID.randomUUID());
        good1.setArticle("123");
        good1.setDescription("Очень хороший");
        good1.setCategory("Товар");
        good1.setPrice(BigDecimal.valueOf(100.0));
        good1.setQuantity(BigDecimal.valueOf(10));
        good1.setCreationTime(LocalDateTime.of(2022, 1, 1, 0, 0));
        good1.setLastUpdateTime(LocalDateTime.of(2022, 1, 1, 0, 0));

        good2.setName("Плохой товар");
        good2.setId(UUID.randomUUID());
        good2.setArticle("321");
        good2.setDescription("Очень плохой");
        good2.setCategory("Товар");
        good2.setPrice(BigDecimal.valueOf(99.0));
        good2.setQuantity(BigDecimal.valueOf(100));

        good3.setName("Товар с одинаковым артикулом");
        good3.setId(UUID.randomUUID());
        good3.setArticle("123");
        good3.setDescription("Очень средний");
        good3.setCategory("Товар");
        good3.setPrice(BigDecimal.valueOf(50.0));
        good3.setQuantity(BigDecimal.valueOf(1000));

        goodFullDto1.setName(good1.getName());
        goodFullDto1.setId(good1.getId());
        goodFullDto1.setArticle(good1.getArticle());
        goodFullDto1.setDescription(good1.getDescription());
        goodFullDto1.setCategory(good1.getCategory());
        goodFullDto1.setPrice(good1.getPrice());
        goodFullDto1.setQuantity(good1.getQuantity());
        goodFullDto1.setLastUpdateTime(good1.getLastUpdateTime());
        goodFullDto1.setCreationTime(good1.getCreationTime());

        goodFullDto2.setName(good2.getName());
        goodFullDto2.setId(good2.getId());
        goodFullDto2.setArticle(good2.getArticle());
        goodFullDto2.setDescription(good2.getDescription());
        goodFullDto2.setCategory(good2.getCategory());
        goodFullDto2.setPrice(good2.getPrice());
        goodFullDto2.setQuantity(good2.getQuantity());
        goodFullDto2.setLastUpdateTime(good2.getLastUpdateTime());
        goodFullDto2.setCreationTime(good2.getCreationTime());

        goodUpdateDto1.setName(good1.getName());
        goodUpdateDto1.setArticle(good1.getArticle());
        goodUpdateDto1.setDescription(good1.getDescription());
        goodUpdateDto1.setCategory(good1.getCategory());
        goodUpdateDto1.setPrice(good1.getPrice());
        goodUpdateDto1.setQuantity(good1.getQuantity());

        goodUpdateDto2.setName(good2.getName());
        goodUpdateDto2.setArticle(good2.getArticle());
        goodUpdateDto2.setDescription(good2.getDescription());
        goodUpdateDto2.setCategory(good2.getCategory());
        goodUpdateDto2.setPrice(good2.getPrice());
        goodUpdateDto2.setQuantity(good2.getQuantity());


    }

    @Test
    void create_CorrectValues_CorrectSave() throws SQLUniqueException {
        //when
        when(mappingUtils.mapUpdateToWarehouseGood(goodUpdateDto1)).thenReturn(good1);
        when(goodRepository.save(good1)).thenReturn(good1);
        when(mappingUtils.mapToWarehouseGoodFullDto(good1)).thenReturn(goodFullDto1);
        //then
        ProductFullDto result = goodService.create(goodUpdateDto1);
        assertEquals(good1.getId(), result.getId());
        assertEquals(good1.getArticle(), result.getArticle());
        assertEquals(good1.getName(), result.getName());
        assertEquals(good1.getCategory(), result.getCategory());
        assertEquals(good1.getDescription(), result.getDescription());
        assertEquals(good1.getPrice(), result.getPrice());
        assertEquals(good1.getQuantity(), result.getQuantity());
        assertEquals(good1.getCreationTime(), result.getCreationTime());
        assertEquals(good1.getLastUpdateTime(), result.getLastUpdateTime());
        verify(goodRepository, times(1)).save(good1);
    }

    @Test
    void readById_validUUID_CorrectFullDto() throws NotFoundByIdException {
        UUID validId = UUID.randomUUID();
        ProductFullDto expectedDto = new ProductFullDto();
        when(goodRepository.existsById(validId)).thenReturn(true);
        when(goodRepository.getReferenceById(validId)).thenReturn(new Product());
        when(mappingUtils.mapToWarehouseGoodFullDto(any(Product.class))).thenReturn(expectedDto);

        // when
        ProductFullDto result = goodService.readById(validId);

        // then
        assertEquals(expectedDto, result);
    }

    @Test
    void readById_invalidUUID_ThrowsNotFoundByIdException() {
        // given
        UUID invalidId = UUID.randomUUID();
        when(goodRepository.existsById(invalidId)).thenReturn(false);

        // then
        assertThrows(NotFoundByIdException.class, () -> goodService.readById(invalidId));
    }

    @Test
    void readById_nullId_ThrowsNotFoundByIdException() {
        // then
        assertThrows(NotFoundByIdException.class, () -> goodService.readById(null));
    }


    @Test
    void readByArticle_validArticle_CorrectFullDto() throws NotFoundByArticleException {
        // given
        String validArticle = "12345";

        when(goodRepository.existsByArticle(validArticle)).thenReturn(true);
        when(goodRepository.getReferenceByArticle(validArticle)).thenReturn(good1);
        ProductFullDto expectedDto = mappingUtils.mapToWarehouseGoodFullDto(good1);

        // when
        ProductFullDto result = goodService.readByArticle(validArticle);

        // then
        assertEquals(expectedDto, result);
    }

    @Test
    void readByArticle_invalidArticle_ThrowsNotFoundByArticleException() {
        // given
        String invalidArticle = "54321";
        when(goodRepository.existsByArticle(invalidArticle)).thenReturn(false);

        // then
        assertThrows(NotFoundByArticleException.class, () -> goodService.readByArticle(invalidArticle));
    }
}
    /*

    @Test
    void readAll_CorrectGoodAdd_NonEmptyList() throws EmptyProductException {
        // given
        List<Product> warehouseGoods = new ArrayList<>();
        ProductSearchDto warehouseGoodSearchDto = new ProductSearchDto();
        warehouseGoodSearchDto.setPageNumber(0);
        warehouseGoodSearchDto.setSize(10);
        warehouseGoods.add(new Product());
        when(goodRepository.findAll()).thenReturn(warehouseGoods);

        // when
        List<ProductFullDto> result = goodService.readAll(warehouseGoodSearchDto);

        // then
        assertFalse(result.isEmpty());
    }

    @Test
    void readAll_EmptyList_ThrowsEmptyGoodsException() {
        // given
        ProductSearchDto warehouseGoodSearchDto = new ProductSearchDto();
        warehouseGoodSearchDto.setSize(10);
        warehouseGoodSearchDto.setPageNumber(0);
        when(goodRepository.findAll()).thenReturn(new ArrayList<>());

        // then
        assertThrows(EmptyProductException.class, () -> goodService.readAll(warehouseGoodSearchDto));
    }

    @Test
    void updateById_invalidId_ThrowsNotFoundByIdException() {
        // given
        UUID id = UUID.randomUUID();
        when(goodRepository.existsById(id)).thenReturn(false);

        // then
        assertThrows(NotFoundByIdException.class, () -> goodService.updateById(new ProductUpdateDto(), id));
    }

    @Test
    void updateById_validId_CorrectFullDto() throws NotFoundByIdException, SQLUniqueException {
        // given
        UUID id = UUID.randomUUID();
        ProductUpdateDto warehouseGoodUpdateDto = ProductUpdateDto.builder()
                .name("Test Good")
                .article("12345")
                .description("Test Description")
                .category("Test Category")
                .price(10.0)
                .quantity(5)
                .build();
        ProductFullDto expectedDto = ProductFullDto.builder()
                .id(id)
                .name("Test Good")
                .article("12345")
                .description("Test Description")
                .category("Test Category")
                .price(10.0)
                .quantity(5)
                .build();

        when(goodRepository.existsById(id)).thenReturn(true);
        when(goodRepository.getReferenceById(id)).thenReturn(Product.builder().build());
        when(mappingUtils.mapToWarehouseGoodFullDto(any(Product.class))).thenReturn(expectedDto);

        // when
        ProductFullDto result = goodService.updateById(warehouseGoodUpdateDto, id);

        // then
        assertEquals(expectedDto, result);
    }

    @Test
    void updateById_articleAlreadyExist_ThrowsSQLUniqueException() {
        // given
        UUID id = UUID.randomUUID();
        ProductUpdateDto warehouseGoodUpdateDto = ProductUpdateDto.builder()
                .name("Test Good")
                .article("12345")
                .description("Test Description")
                .category("Test Category")
                .price(10.0)
                .quantity(5)
                .build();

        when(goodRepository.existsById(id)).thenReturn(true);
        when(goodRepository.getReferenceById(id)).thenReturn(Product.builder().article("54321").build());
        when(goodRepository.existsByArticle(warehouseGoodUpdateDto.getArticle())).thenReturn(true);

        // then
        assertThrows(SQLUniqueException.class, () -> goodService.updateById(warehouseGoodUpdateDto, id));
    }

    @Test
    void deleteById_validId_CorrectDelete() throws NotFoundByIdException {
        // given
        UUID validId = UUID.randomUUID();
        when(goodRepository.existsById(validId)).thenReturn(true);

        // when
        goodService.deleteById(validId);

        // then
        verify(goodRepository, times(1)).deleteById(validId);
    }

    @Test
    void deleteById_invalidId_ThrowsNotFoundByIdException() throws NotFoundByIdException {
        // given
        UUID validId = UUID.randomUUID();
        when(goodRepository.existsById(validId)).thenReturn(true);

        // when
        goodService.deleteById(validId);

        // then
        verify(goodRepository, times(1)).deleteById(validId);
    }

    @Test
    void deleteByArticle_invalidArticle_ThrowsNotFoundByArticleException() {
        // given
        String article = "12345";
        when(goodRepository.existsByArticle(article)).thenReturn(false);

        // when
        assertThrows(NotFoundByArticleException.class, () -> goodService.deleteByArticle(article));

        // then
        verify(goodRepository, never()).deleteByArticle(article);
    }

    @Test
    void deleteByArticle_nullArticle_ThrowsNotFoundByArticleException() {
        // given
        String article = null;

        // when
        assertThrows(NotFoundByArticleException.class, () -> goodService.deleteByArticle(article));

        // then
        verify(goodRepository, never()).deleteByArticle(article);
    }

    @Test
    void deleteByArticle_emptyArticle_ThrowsNotFoundByArticleException() {
        // given
        String article = "";

        // when
        assertThrows(NotFoundByArticleException.class, () -> goodService.deleteByArticle(article));

        // then
        verify(goodRepository, never()).deleteByArticle(article);
    }

    @Test
    void deleteAll_doesNotThrowException_CorrectDelete() {
        // given
        List<Product> goods = new ArrayList<>();
        goods.add(new Product());
        goods.add(new Product());
        when(goodRepository.findAll()).thenReturn(goods);

        // when
        assertDoesNotThrow(() -> goodService.deleteAll());

        // then
        verify(goodRepository, times(1)).deleteAll();
    }
}
     */
