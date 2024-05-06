package com.warehousesystem.app.service;

import com.warehousesystem.app.dto.order.OrderCreateDto;
import com.warehousesystem.app.handler.Exception.CustomerIdNullException;

import java.util.UUID;

public interface OrderService {

    UUID create(OrderCreateDto orderCreateDto, Long customerId) throws CustomerIdNullException;
}
