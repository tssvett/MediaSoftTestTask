package com.warehousesystem.app.service.impl;


import com.warehousesystem.app.dto.WarehouseGoodFullDto;
import com.warehousesystem.app.dto.WarehouseGoodSearchDto;
import com.warehousesystem.app.dto.WarehouseGoodUpdateDto;
import com.warehousesystem.app.handler.exception.EmptyGoodsException;
import com.warehousesystem.app.handler.exception.NotFoundByArticleException;
import com.warehousesystem.app.handler.exception.NotFoundByIdException;
import com.warehousesystem.app.handler.exception.SQLUniqueException;
import com.warehousesystem.app.model.WarehouseGood;
import com.warehousesystem.app.repository.WarehouseGoodRepository;
import com.warehousesystem.app.utils.MappingUtils;
import org.junit.jupiter.api.BeforeAll;
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

    static WarehouseGood good1;
    static WarehouseGood good2;
    static WarehouseGood good3;
    static WarehouseGood good4;
    static WarehouseGood good5;

    static WarehouseGoodUpdateDto goodUpdateDto1;
    static WarehouseGoodUpdateDto goodUpdateDto2;
    static WarehouseGoodUpdateDto goodUpdateDto3;

    static WarehouseGoodFullDto goodFullDto1;
    static WarehouseGoodFullDto goodFullDto2;
    static WarehouseGoodFullDto goodFullDto3;


    @BeforeAll
    static void setUp() {
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
    void create_CorrectValues_CorrectSave() throws SQLUniqueException {
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
    void readById_validUUID_CorrectFullDto() throws NotFoundByIdException {
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
        WarehouseGoodFullDto expectedDto = mappingUtils.mapToWarehouseGoodFullDto(good1);

        // when
        WarehouseGoodFullDto result = goodService.readByArticle(validArticle);

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

    @Test
    void readAll_CorrectGoodAdd_NonEmptyList() throws EmptyGoodsException {
        // given
        List<WarehouseGood> warehouseGoods = new ArrayList<>();
        WarehouseGoodSearchDto warehouseGoodSearchDto = new WarehouseGoodSearchDto();
        warehouseGoodSearchDto.setPageNumber(0);
        warehouseGoodSearchDto.setSize(10);
        warehouseGoods.add(new WarehouseGood());
        when(goodRepository.findAll()).thenReturn(warehouseGoods);

        // when
        List<WarehouseGoodFullDto> result = goodService.readAll(warehouseGoodSearchDto);

        // then
        assertFalse(result.isEmpty());
    }

    @Test
    void readAll_EmptyList_ThrowsEmptyGoodsException() {
        // given
        WarehouseGoodSearchDto warehouseGoodSearchDto = new WarehouseGoodSearchDto();
        warehouseGoodSearchDto.setSize(10);
        warehouseGoodSearchDto.setPageNumber(0);
        when(goodRepository.findAll()).thenReturn(new ArrayList<>());

        // then
        assertThrows(EmptyGoodsException.class, () -> goodService.readAll(warehouseGoodSearchDto));
    }

    @Test
    void updateById_invalidId_ThrowsNotFoundByIdException() {
        // given
        UUID id = UUID.randomUUID();
        when(goodRepository.existsById(id)).thenReturn(false);

        // then
        assertThrows(NotFoundByIdException.class, () -> goodService.updateById(new WarehouseGoodUpdateDto(), id));
    }

    @Test
    void updateById_validId_CorrectFullDto() throws NotFoundByIdException, SQLUniqueException {
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
    void updateById_articleAlreadyExist_ThrowsSQLUniqueException() {
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
