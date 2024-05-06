package com.warehousesystem.app.controller;

import com.warehousesystem.app.dto.order.OrderCreateDto;
import com.warehousesystem.app.handler.Exception.CustomerIdNullException;
import com.warehousesystem.app.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Validated
@CrossOrigin
public class OrderController {

    @Autowired
    public OrderService orderService;


    @PostMapping("/order")
    public ResponseEntity<UUID> createOrder(@Valid @RequestBody OrderCreateDto orderCreateDto, @RequestHeader(value = "customerId", defaultValue = "" ) Long customerId) throws CustomerIdNullException {
        UUID orderId = orderService.create(orderCreateDto, customerId);
        return new ResponseEntity<>(orderId, HttpStatus.OK);
    }
}
