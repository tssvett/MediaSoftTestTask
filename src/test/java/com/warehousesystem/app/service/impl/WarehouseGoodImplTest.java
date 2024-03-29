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
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    void create_succesfullyCreated() throws SQLUniqueException {
        //when
        when(goodService.create(goodFullDto1)).thenReturn(goodFullDto1);

        //then
        assertEquals(goodFullDto1, goodService.create(goodFullDto1));

    }




    @Test
    void readById_succesfullyRead() throws NotFoundByIdException {

        //when
        when(goodRepository.existsById(good1.getId())).thenReturn(true);
        when(goodService.readById(good1.getId())).thenReturn(goodFullDto1);
        //then
        assertEquals(goodFullDto1, goodService.readById(good1.getId()));
    }

    @Test
    void readById_throwsNotFoundByIdException() {
        //when
        when(goodRepository.existsById(good1.getId())).thenReturn(false);
        //then
        assertThrows(NotFoundByIdException.class, () -> goodService.readById(good1.getId()));
    }

    @Test
    void readByArticle_succesfullyRead() throws NotFoundByArticleException {
        //when
        when(goodRepository.existsByArticle(good1.getArticle())).thenReturn(true);
        when(goodService.readByArticle(good1.getArticle())).thenReturn(goodFullDto1);
        //then
        assertEquals(goodFullDto1, goodService.readByArticle(good1.getArticle()));
    }

    @Test
    void readByArticle_throwsNotFoundByArticleException() {
        //when
        when(goodRepository.existsByArticle(good1.getArticle())).thenReturn(false);
        //then
        assertThrows(NotFoundByArticleException.class, () -> goodService.readByArticle(good1.getArticle()));
    }

    @Test
    void readAll_succesfullyRead() throws EmptyGoodsException {
        // given
        List<WarehouseGood> goods = List.of(good1, good2);
        List<WarehouseGoodFullDto> expected = List.of(goodFullDto1, goodFullDto2);

        // when
        when(goodRepository.findAll()).thenReturn(goods);
        when(goods.stream().map(mappingUtils::mapToWarehouseGoodFullDto).collect(Collectors.toList())).thenReturn(expected);
        List<WarehouseGoodFullDto> result = goodService.readAll();

        // then
        assertEquals(expected, result);

    }

    @Test
    void readAll_throwsEmptyGoodsException() {
        //given
        List<WarehouseGood> goods = List.of();

        //when
        when(goodRepository.findAll()).thenReturn(goods);

        //then
        assertThrows(EmptyGoodsException.class, () -> goodService.readAll());
    }

    @Test
    void updateById_succesfullyUpdated() throws SQLUniqueException, NotFoundByIdException {

        //when
        when(goodRepository.existsById(good1.getId())).thenReturn(true);

        when(mappingUtils.mapUpdateToWarehouseGood(goodUpdateDto2)).thenReturn(good1);
        when(goodService.updateById(goodUpdateDto2, good1.getId())).thenReturn(goodUpdateDto2);
        //then
        assertEquals(goodUpdateDto2, goodService.updateById(goodUpdateDto2, good1.getId()));
    }

    @Test
    void updateById_throwsNotFoundByIdException() {
        //given
        WarehouseGoodUpdateDto updatedGood2 = mappingUtils.mapToWarehouseGoodUpdateDto(good2);
        //when
        when(goodRepository.existsById(good1.getId())).thenReturn(false);
        //then
        assertThrows(NotFoundByIdException.class, () -> goodService.updateById(updatedGood2, good1.getId()));
    }

    @Test
    void updateByArticle_succesfullyUpdated() throws SQLUniqueException, NotFoundByArticleException {
        //when
        when(goodRepository.existsByArticle(good1.getArticle())).thenReturn(true);
        when(goodRepository.getReferenceByArticle(good1.getArticle())).thenReturn(good1);
        //then
        when(goodService.updateByArticle(goodUpdateDto2, good1.getArticle())).thenReturn(goodUpdateDto2);
        assertEquals(goodUpdateDto2, goodService.updateByArticle(goodUpdateDto2, good1.getArticle()));
    }

    @Test
    void updateByArticle_throwsNotFoundByArticleException() {
        //given
        WarehouseGoodUpdateDto updatedGood2 = mappingUtils.mapToWarehouseGoodUpdateDto(good2);
        //when
        when(goodRepository.existsByArticle(good1.getArticle())).thenReturn(false);
        //then
        assertThrows(NotFoundByArticleException.class, () -> goodService.updateByArticle(updatedGood2, good1.getArticle()));
    }

    @Test
    void deleteById_succesfullyDeleted() throws NotFoundByIdException {
        //when
        when(goodRepository.existsById(good1.getId())).thenReturn(true);
        //then
        goodService.deleteById(good1.getId());
        verify(goodRepository, times(1)).deleteById(good1.getId());
    }

    @Test
    void deleteById_throwsNotFoundByIdException() {
        //when
        when(goodRepository.existsById(good1.getId())).thenReturn(false);
        //then
        assertThrows(NotFoundByIdException.class, () -> goodService.deleteById(good1.getId()));
    }

    @Test
    void deleteByArticle_succesfullyDeleted() throws NotFoundByArticleException {
        //when
        when(goodRepository.existsByArticle(good1.getArticle())).thenReturn(true);

        //then
        goodService.deleteByArticle(good1.getArticle());
        verify(goodRepository, times(1)).deleteByArticle(good1.getArticle());
    }

    @Test
    void deleteByArticle_throwsNotFoundByArticleException() {

        //when
        when(goodRepository.existsByArticle(good1.getArticle())).thenReturn(false);

        //then
        assertThrows(NotFoundByArticleException.class, () -> goodService.deleteByArticle(good1.getArticle()));
    }

    @Test
    void deleteAll_succesfullyDeleted() throws EmptyGoodsException {
        //given
        List<WarehouseGood> goods = List.of(good1, good2);

        //when
        when(goodRepository.findAll()).thenReturn(goods);

        //then
        goodService.deleteAll();
        verify(goodRepository, times(1)).deleteAll();
    }

    @Test
    void deleteAll_throwsEmptyGoodsException() {
        //given
        List<WarehouseGood> goods = List.of();
        //when
        when(goodRepository.findAll()).thenReturn(goods);
        //then

        assertThrows(EmptyGoodsException.class, () -> goodService.deleteAll());
    }


}
