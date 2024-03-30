package com.warehousesystem.app.service.impl;


import com.warehousesystem.app.dto.WarehouseGoodFullDto;
import com.warehousesystem.app.dto.WarehouseGoodUpdateDto;
import com.warehousesystem.app.handler.Exception.EmptyGoodsException;
import com.warehousesystem.app.handler.Exception.NotFoundByArticleException;
import com.warehousesystem.app.handler.Exception.NotFoundByIdException;
import com.warehousesystem.app.handler.Exception.SQLUniqueException;
import com.warehousesystem.app.model.WarehouseGood;
import com.warehousesystem.app.repository.WarehouseGoodRepository;
import com.warehousesystem.app.utils.MappingUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class WarehouseGoodImplTest {

    @Mock
    WarehouseGoodRepository goodRepository;

    @Mock
    MappingUtils mappingUtils;

    @InjectMocks
    WarehouseGoodServiceImpl goodService;

    WarehouseGood good1;
    WarehouseGood good2;
    WarehouseGood good3;
    WarehouseGood good4;
    WarehouseGood good5;

    WarehouseGoodUpdateDto goodUpdateDto1;
    WarehouseGoodUpdateDto goodUpdateDto2;
    WarehouseGoodUpdateDto goodUpdateDto3;

    WarehouseGoodFullDto goodFullDto1;
    WarehouseGoodFullDto goodFullDto2;
    WarehouseGoodFullDto goodFullDto3;


    @BeforeEach
    void setUp() {
        good1 = new WarehouseGood();
        good2 = new WarehouseGood();
        good3 = new WarehouseGood();
        good4 = new WarehouseGood();
        good5 = new WarehouseGood();

        goodUpdateDto1 = new WarehouseGoodUpdateDto();
        goodUpdateDto2 = new WarehouseGoodUpdateDto();
        goodUpdateDto3 = new WarehouseGoodUpdateDto();

        goodFullDto1 = new WarehouseGoodFullDto();
        goodFullDto2 = new WarehouseGoodFullDto();
        goodFullDto3 = new WarehouseGoodFullDto();
        //given
        good1.setName("Хороший товар");
        good1.setId(UUID.randomUUID());
        good1.setArticle("123");
        good1.setDescription("Очень хороший");
        good1.setCategory("Товар");
        good1.setPrice(100.0);
        good1.setQuantity(10);
        good1.setCreationTime(LocalDateTime.of(2022, 1, 1, 0, 0));
        good1.setLastUpdateTime(LocalDateTime.of(2022, 1, 1, 0, 0));

        good2.setName("Плохой товар");
        good2.setId(UUID.randomUUID());
        good2.setArticle("321");
        good2.setDescription("Очень плохой");
        good2.setCategory("Товар");
        good2.setPrice(99.0);
        good2.setQuantity(100);

        good3.setName("Товар с одинаковым артикулом");
        good3.setId(UUID.randomUUID());
        good3.setArticle("123");
        good3.setDescription("Очень средний");
        good3.setCategory("Товар");
        good3.setPrice(50.0);
        good3.setQuantity(1000);

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
    void test_create_valid_input() throws SQLUniqueException {
        //when
        when(mappingUtils.mapUpdateToWarehouseGood(goodUpdateDto1)).thenReturn(good1);
        when(goodRepository.save(good1)).thenReturn(good1);
        when(mappingUtils.mapToWarehouseGoodFullDto(good1)).thenReturn(goodFullDto1);
        //then
        WarehouseGoodFullDto result = goodService.create(goodUpdateDto1);
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
    void test_save_to_repository() throws SQLUniqueException {
        //when
        when(mappingUtils.mapUpdateToWarehouseGood(goodUpdateDto1)).thenReturn(good1);
        when(goodRepository.save(good1)).thenReturn(good1);
        when(mappingUtils.mapToWarehouseGoodFullDto(good1)).thenReturn(goodFullDto1);

        WarehouseGoodFullDto  result = goodService.create(goodUpdateDto1);
        verify(goodRepository, times(1)).save(good1);
    }

    @Test
    void test_returns_correct_warehouse_good_full_dto_with_valid_uuid() throws NotFoundByIdException {
        // given
        UUID validId = UUID.randomUUID();
        WarehouseGoodFullDto expectedDto = new WarehouseGoodFullDto();
        when(goodRepository.existsById(validId)).thenReturn(true);
        when(goodRepository.getReferenceById(validId)).thenReturn(new WarehouseGood());
        when(mappingUtils.mapToWarehouseGoodFullDto(any(WarehouseGood.class))).thenReturn(expectedDto);

        // when
        WarehouseGoodFullDto result = goodService.readById(validId);

        // then
        assertEquals(expectedDto, result);
    }

    @Test
    void test_throws_not_found_by_id_exception_with_invalid_uuid() {
        // given
        UUID invalidId = UUID.randomUUID();
        when(goodRepository.existsById(invalidId)).thenReturn(false);

        // then
        assertThrows(NotFoundByIdException.class, () -> goodService.readById(invalidId));
    }

    @Test
    void test_throws_not_found_by_id_exception_with_null_uuid() {
        // then
        assertThrows(NotFoundByIdException.class, () -> goodService.readById(null));
    }


    @Test
    void test_valid_article_returns_warehouse_good_full_dto() throws NotFoundByArticleException {
        // given
        String validArticle = "12345";

        when(goodRepository.existsByArticle(validArticle)).thenReturn(true);
        when(goodRepository.getReferenceByArticle(validArticle)).thenReturn(good1);
        WarehouseGoodFullDto expectedDto = mappingUtils.mapToWarehouseGoodFullDto(good1);

        // when
        WarehouseGoodFullDto result = goodService.readByArticle(validArticle);

        // then
        assertEquals(expectedDto, result);
    }

    @Test
    void test_invalid_article_throws_not_found_by_article_exception() {
        // given
        String invalidArticle = "54321";
        when(goodRepository.existsByArticle(invalidArticle)).thenReturn(false);

        // then
        assertThrows(NotFoundByArticleException.class, () -> goodService.readByArticle(invalidArticle));
    }

    @Test
    void test_return_list_when_repository_not_empty() throws EmptyGoodsException {
        // given
        List<WarehouseGood> warehouseGoods = new ArrayList<>();
        warehouseGoods.add(new WarehouseGood());
        when(goodRepository.findAll()).thenReturn(warehouseGoods);

        // when
        List<WarehouseGoodFullDto> result = goodService.readAll();

        // then
        assertFalse(result.isEmpty());
    }

    @Test
    void test_throw_exception_when_repository_empty() {
        // given
        when(goodRepository.findAll()).thenReturn(new ArrayList<>());

        // then
        assertThrows(EmptyGoodsException.class, () -> goodService.readAll());
    }

    @Test
    void test_throw_exception_when_id_not_found() {
        // given
        UUID id = UUID.randomUUID();
        when(goodRepository.existsById(id)).thenReturn(false);

        // then
        assertThrows(NotFoundByIdException.class, () -> goodService.updateById(new WarehouseGoodUpdateDto(), id));
    }

    @Test
    void test_update_valid_input() throws NotFoundByIdException, SQLUniqueException {
        // given
        UUID id = UUID.randomUUID();
        WarehouseGoodUpdateDto warehouseGoodUpdateDto = WarehouseGoodUpdateDto.builder()
                .name("Test Good")
                .article("12345")
                .description("Test Description")
                .category("Test Category")
                .price(10.0)
                .quantity(5)
                .build();
        WarehouseGoodFullDto expectedDto = WarehouseGoodFullDto.builder()
                .id(id)
                .name("Test Good")
                .article("12345")
                .description("Test Description")
                .category("Test Category")
                .price(10.0)
                .quantity(5)
                .build();

        when(goodRepository.existsById(id)).thenReturn(true);
        when(goodRepository.getReferenceById(id)).thenReturn(WarehouseGood.builder().build());
        when(mappingUtils.mapToWarehouseGoodFullDto(any(WarehouseGood.class))).thenReturn(expectedDto);

        // when
        WarehouseGoodFullDto result = goodService.updateById(warehouseGoodUpdateDto, id);

        // then
        assertEquals(expectedDto, result);
    }

    @Test
    public void test_update_sql_unique_exception() {
        // given
        UUID id = UUID.randomUUID();
        WarehouseGoodUpdateDto warehouseGoodUpdateDto = WarehouseGoodUpdateDto.builder()
                .name("Test Good")
                .article("12345")
                .description("Test Description")
                .category("Test Category")
                .price(10.0)
                .quantity(5)
                .build();

        when(goodRepository.existsById(id)).thenReturn(true);
        when(goodRepository.getReferenceById(id)).thenReturn(WarehouseGood.builder().article("54321").build());
        when(goodRepository.existsByArticle(warehouseGoodUpdateDto.getArticle())).thenReturn(true);

        // then
        assertThrows(SQLUniqueException.class, () -> goodService.updateById(warehouseGoodUpdateDto, id));
    }

    @Test
    public void test_throw_not_found_by_id_exception() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(goodRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(NotFoundByIdException.class, () -> goodService.updateById(any(WarehouseGoodUpdateDto.class), id));
    }

    @Test
    void test_deletes_existing_warehouse_good_by_id() throws NotFoundByIdException {
        // given
        UUID validId = UUID.randomUUID();
        when(goodRepository.existsById(validId)).thenReturn(true);

        // when
        goodService.deleteById(validId);

        // then
        verify(goodRepository, times(1)).deleteById(validId);
    }

    @Test
    void test_deletes_only_warehouse_good_with_specified_id() throws NotFoundByIdException {
        // given
        UUID validId = UUID.randomUUID();
        when(goodRepository.existsById(validId)).thenReturn(true);

        // when
        goodService.deleteById(validId);

        // then
        verify(goodRepository, times(1)).deleteById(validId);
    }

    @Test
    void test_throws_not_found_by_id_exception_when_deleting_non_existing_warehouse_good() {
        // given
        UUID invalidId = UUID.randomUUID();
        when(goodRepository.existsById(invalidId)).thenReturn(false);

        // then
        assertThrows(NotFoundByIdException.class, () -> goodService.deleteById(invalidId));
    }

    @Test
    void test_does_not_delete_warehouse_good_when_article_does_not_exist() {
        // given
        String article = "12345";
        when(goodRepository.existsByArticle(article)).thenReturn(false);

        // when
        assertThrows(NotFoundByArticleException.class, () -> goodService.deleteByArticle(article));

        // then
        verify(goodRepository, never()).deleteByArticle(article);
    }

    @Test
    void test_does_not_delete_warehouse_good_when_article_is_null() {
        // given
        String article = null;

        // when
        assertThrows(NotFoundByArticleException.class, () -> goodService.deleteByArticle(article));

        // then
        verify(goodRepository, never()).deleteByArticle(article);
    }

    @Test
    void test_does_not_delete_warehouse_good_when_article_is_empty_string() {
        // given
        String article = "";

        // when
        assertThrows(NotFoundByArticleException.class, () -> goodService.deleteByArticle(article));

        // then
        verify(goodRepository, never()).deleteByArticle(article);
    }

    @Test
    void test_deletes_all_goods_when_goods_exist() {
        // given
        List<WarehouseGood> goods = new ArrayList<>();
        goods.add(new WarehouseGood());
        goods.add(new WarehouseGood());
        when(goodRepository.findAll()).thenReturn(goods);

        // when
        assertDoesNotThrow(() -> goodService.deleteAll());

        // then
        verify(goodRepository, times(1)).deleteAll();
    }

    @Test
    void test_does_not_throw_exception_when_goods_exist() {
        // given
        List<WarehouseGood> goods = new ArrayList<>();
        goods.add(new WarehouseGood());
        goods.add(new WarehouseGood());
        when(goodRepository.findAll()).thenReturn(goods);

        // when
        assertDoesNotThrow(() -> goodService.deleteAll());

        // then
        verify(goodRepository, times(1)).deleteAll();
    }

}
