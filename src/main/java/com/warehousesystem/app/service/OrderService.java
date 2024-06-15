package com.warehousesystem.app.service;

import com.warehousesystem.app.dto.order.OrderCreateDto;
import com.warehousesystem.app.dto.order.OrderGetResponseDto;
import com.warehousesystem.app.dto.order.OrderUpdateDto;
import com.warehousesystem.app.dto.product.ProductOrderDto;
import com.warehousesystem.app.dto.status.StatusResponseDto;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    UUID createOrder(OrderCreateDto orderCreateDto, Long customerId);

    OrderUpdateDto updateOrderById(UUID orderId, List<ProductOrderDto> products, Long customerId);

    OrderGetResponseDto getOrderById(UUID orderId, Long customerId);

    UUID deleteOrderById(UUID orderId, Long customerId);

    StatusResponseDto setOrderStatusById(UUID orderId, StatusResponseDto status, Long customerId);
}
