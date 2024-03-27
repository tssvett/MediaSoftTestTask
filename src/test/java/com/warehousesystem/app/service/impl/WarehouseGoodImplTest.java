package com.warehousesystem.app.service.impl;


import com.warehousesystem.app.handler.Exception.EmptyGoodsException;
import com.warehousesystem.app.handler.Exception.NotFoundByArticleException;
import com.warehousesystem.app.handler.Exception.NotFoundByIdException;
import com.warehousesystem.app.handler.Exception.SQLUniqueException;
import com.warehousesystem.app.model.WarehouseGood;
import com.warehousesystem.app.repository.WarehouseGoodRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;


import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class WarehouseGoodImplTest {

    @Mock
    WarehouseGoodRepository goodRepository;

    @InjectMocks
    WarehouseGoodServiceImpl goodService;

    WarehouseGood good1 = new WarehouseGood();
    WarehouseGood good2 = new WarehouseGood();
    WarehouseGood good3 = new WarehouseGood();
    WarehouseGood good4 = new WarehouseGood();
    WarehouseGood good5 = new WarehouseGood();


    @BeforeEach
    void setUp() {
        //given
        good1.setName("Хороший товар");
        good1.setId(UUID.randomUUID());
        good1.setArticle("123");
        good1.setDescription("Очень хороший");
        good1.setCategory("Товар");
        good1.setPrice(100.0);
        good1.setQuantity(10);

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

        good4 = good1;
        good4.setArticle("12423");


    }

    @Test
    void create_succesfullyCreated() throws SQLUniqueException {
        //when
        when(goodRepository.save(good1)).thenReturn(good1);
        WarehouseGood actual = goodService.create(good1);

        //then
        assertEquals(good1, actual);
        verify(goodRepository, times(1)).save(good1);
        verifyNoMoreInteractions(goodRepository);
    }




    @Test
    void readById_succesfullyRead() throws SQLUniqueException, NotFoundByIdException {
        //when
        when(goodRepository.existsById(good1.getId())).thenReturn(true);
        when(goodService.readById(good1.getId())).thenReturn(good1);
        //then
        assertEquals(good1, goodService.readById(good1.getId()));
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
        when(goodService.readByArticle(good1.getArticle())).thenReturn(good1);
        //then
        assertEquals(good1, goodService.readByArticle(good1.getArticle()));
    }

    @Test
    void readByArticle_throwsNotFoundByArticleException() {
        //when
        when(goodRepository.existsByArticle(good1.getArticle())).thenReturn(false);
        //then
        assertThrows(NotFoundByArticleException.class, () -> goodService.readByArticle(good1.getArticle()));
    }

    @Test
    void readAll_succesfullyRead() throws EmptyGoodsException, SQLUniqueException {
        //given
        List<WarehouseGood> goods = List.of(good1, good2);
        //when
        when(goodRepository.findAll()).thenReturn(goods);
        //then
        assertThat(goodService.readALl()).isEqualTo(goods);

    }

    @Test
    void readAll_throwsEmptyGoodsException() throws EmptyGoodsException {
        //given
        List<WarehouseGood> goods = List.of();

        //when
        when(goodRepository.findAll()).thenReturn(goods);

        //then
        assertThrows(EmptyGoodsException.class, () -> goodService.readALl());
    }

    @Test
    void updateById_succesfullyUpdated() throws SQLUniqueException, NotFoundByIdException {
        //when
        when(goodRepository.existsById(good1.getId())).thenReturn(true);
        when(goodService.updateById(good2, good1.getId())).thenReturn(good2);
        //then
        assertEquals(good2, goodService.updateById(good2, good1.getId()));
    }

    @Test
    void updateById_throwsNotFoundByIdException() {
        //when
        when(goodRepository.existsById(good1.getId())).thenReturn(false);
        //then
        assertThrows(NotFoundByIdException.class, () -> goodService.updateById(good2, good1.getId()));
    }

    @Test
    void updateByArticle_succesfullyUpdated() throws SQLUniqueException, NotFoundByArticleException {
        //when
        when(goodRepository.existsByArticle(good1.getArticle())).thenReturn(true);
        when(goodRepository.getReferenceByArticle(good1.getArticle())).thenReturn(good1);
        //then
        when(goodService.updateByArticle(good2, good1.getArticle())).thenReturn(good2);
        assertEquals(good1, goodService.updateByArticle(good2, good1.getArticle()));
    }

    @Test
    void updateByArticle_throwsNotFoundByArticleException() {
        //when
        when(goodRepository.existsByArticle(good1.getArticle())).thenReturn(false);
        //then
        assertThrows(NotFoundByArticleException.class, () -> goodService.updateByArticle(good2, good1.getArticle()));
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
        List<WarehouseGood> goods = List.of(good4, good5);

        //when
        when(goodRepository.findAll()).thenReturn(goods);

        //then
        goodService.deleteAll();
        verify(goodRepository, times(1)).deleteAll();
    }

    @Test
    void deleteAll_throwsEmptyGoodsException() throws EmptyGoodsException {
        //given
        List<WarehouseGood> goods = List.of();
        //when
        when(goodRepository.findAll()).thenReturn(goods);
        //then

        assertThrows(EmptyGoodsException.class, () -> goodService.deleteAll());
    }


}
