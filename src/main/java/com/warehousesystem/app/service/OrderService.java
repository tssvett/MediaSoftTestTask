package com.warehousesystem.app.service;

import com.warehousesystem.app.dto.order.OrderCreateDto;
import com.warehousesystem.app.dto.order.OrderGetResponseDto;
import com.warehousesystem.app.dto.order.OrderUpdateDto;
import com.warehousesystem.app.dto.product.ProductOrderDto;
import com.warehousesystem.app.dto.status.StatusResponseDto;
import com.warehousesystem.app.handler.Exception.*;
import com.warehousesystem.app.model.PreparedProduct;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    UUID create(OrderCreateDto orderCreateDto, Long customerId) throws CustomerIdNullException, NotEnoughProductsException, UnavailableProductException;

    OrderUpdateDto update(UUID orderId, List<ProductOrderDto> products, Long customerId) throws CustomerIdNullException, NotEnoughProductsException, UnavailableProductException, UpdateOrderException, WrongCustomerIdException;

    OrderGetResponseDto get(UUID orderId, Long customerId) throws CustomerIdNullException, WrongCustomerIdException;

    UUID delete(UUID orderId, Long customerId) throws CustomerIdNullException, WrongCustomerIdException, UpdateOrderException;

    StatusResponseDto setStatus(UUID orderId, StatusResponseDto status, Long customerId) throws CustomerIdNullException, WrongCustomerIdException;

}
