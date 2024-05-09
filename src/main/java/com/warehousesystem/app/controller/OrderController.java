package com.warehousesystem.app.controller;

import com.warehousesystem.app.dto.order.OrderCreateDto;
import com.warehousesystem.app.dto.order.OrderGetResponseDto;
import com.warehousesystem.app.dto.order.OrderUpdateDto;
import com.warehousesystem.app.dto.product.ProductOrderDto;
import com.warehousesystem.app.dto.status.StatusResponseDto;
import com.warehousesystem.app.handler.Exception.*;
import com.warehousesystem.app.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Validated
@CrossOrigin
public class OrderController {

    @Autowired
    public OrderService orderService;


    @PostMapping("/order")
    public ResponseEntity<UUID> createOrder(@Valid @RequestBody OrderCreateDto orderCreateDto, @RequestHeader(value = "customerId", defaultValue = "") Long customerId) throws CustomerIdNullException, NotEnoughProductsException, UnavailableProductException {
        UUID orderId = orderService.create(orderCreateDto, customerId);
        return new ResponseEntity<>(orderId, HttpStatus.OK);
    }

    @PatchMapping("/order/{orderId}")
    public ResponseEntity<OrderUpdateDto> updateOrder(@Valid @PathVariable("orderId") UUID orderId, @Valid @RequestBody List<ProductOrderDto> products,
                                                      @RequestHeader(value = "customerId", defaultValue = "") Long customerId) throws CustomerIdNullException, NotEnoughProductsException, UnavailableProductException, UpdateOrderException, WrongCustomerIdException {
        OrderUpdateDto orderUpdateDto = orderService.update(orderId, products, customerId);
        return new ResponseEntity<>(orderUpdateDto, HttpStatus.OK);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<OrderGetResponseDto> getOrder(@PathVariable("orderId") UUID orderId, @RequestHeader(value = "customerId", defaultValue = "") Long customerId) throws CustomerIdNullException, WrongCustomerIdException {
        OrderGetResponseDto orderGetResponseDto = orderService.get(orderId, customerId);
        return new ResponseEntity<>(orderGetResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/order/{orderId}")
    public ResponseEntity<UUID> deleteOrder(@PathVariable("orderId") UUID orderId, @RequestHeader(value = "customerId", defaultValue = "") Long customerId) throws CustomerIdNullException, WrongCustomerIdException, UpdateOrderException {
        UUID deletedOrderId = orderService.delete(orderId, customerId);
        return new ResponseEntity<>(deletedOrderId, HttpStatus.OK);
    }

    @PatchMapping("/order/{orderId}/status")
    public ResponseEntity<StatusResponseDto> updateOrderStatus(@Valid @PathVariable("orderId") UUID orderId, @Valid @RequestBody StatusResponseDto status, @RequestHeader(value = "customerId", defaultValue = "") Long customerId) throws CustomerIdNullException, WrongCustomerIdException {
        StatusResponseDto statusResponseDto = orderService.setStatus(orderId, status, customerId);
        return new ResponseEntity<>(statusResponseDto, HttpStatus.OK);
    }
}


