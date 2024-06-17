package com.warehousesystem.app.service;

import com.warehousesystem.app.dto.delivery.DeliveryDto;
import com.warehousesystem.app.dto.order.OrderCreateDto;
import com.warehousesystem.app.dto.order.OrderGetResponseDto;
import com.warehousesystem.app.dto.order.OrderUpdateDto;
import com.warehousesystem.app.dto.product.ProductOrderDto;
import com.warehousesystem.app.dto.status.StatusResponseDto;
import dev.tssvett.handler.exception.CustomerIdNullException;
import dev.tssvett.handler.exception.NotEnoughProductsException;
import dev.tssvett.handler.exception.UnavailableProductException;
import dev.tssvett.handler.exception.UpdateOrderException;
import dev.tssvett.handler.exception.WrongCustomerIdException;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public interface OrderService {

    UUID create(OrderCreateDto orderCreateDto, Long customerId) throws CustomerIdNullException, NotEnoughProductsException, UnavailableProductException;

    OrderUpdateDto update(UUID orderId, List<ProductOrderDto> products, Long customerId) throws CustomerIdNullException, NotEnoughProductsException, UnavailableProductException, UpdateOrderException, WrongCustomerIdException;

    OrderGetResponseDto get(UUID orderId, Long customerId) throws CustomerIdNullException, WrongCustomerIdException;

    UUID delete(UUID orderId, Long customerId) throws CustomerIdNullException, WrongCustomerIdException, UpdateOrderException;

    StatusResponseDto setStatus(UUID orderId, StatusResponseDto status, Long customerId) throws CustomerIdNullException, WrongCustomerIdException;

    DeliveryDto setDelivery(UUID orderId, DeliveryDto deliveryDto, Long customerId) throws CustomerIdNullException, WrongCustomerIdException;

    UUID startOrderConfirmProcess(UUID orderId, Long customerId) throws ExecutionException, InterruptedException, CustomerIdNullException;
}
