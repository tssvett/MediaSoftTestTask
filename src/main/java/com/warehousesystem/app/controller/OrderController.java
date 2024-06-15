package com.warehousesystem.app.controller;

import com.warehousesystem.app.dto.order.OrderCreateDto;
import com.warehousesystem.app.dto.order.OrderGetResponseDto;
import com.warehousesystem.app.dto.order.OrderUpdateDto;
import com.warehousesystem.app.dto.product.ProductOrderDto;
import com.warehousesystem.app.dto.status.StatusResponseDto;
import com.warehousesystem.app.handler.exception.CustomerIdNullException;
import com.warehousesystem.app.handler.exception.NotEnoughProductsException;
import com.warehousesystem.app.handler.exception.UnavailableProductException;
import com.warehousesystem.app.handler.exception.UpdateOrderException;
import com.warehousesystem.app.handler.exception.WrongCustomerIdException;
import com.warehousesystem.app.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@Validated
@CrossOrigin
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/order")
    @ResponseStatus(HttpStatus.CREATED)
    public UUID createOrder(@Valid @RequestBody OrderCreateDto orderCreateDto, @RequestHeader(value = "customerId", defaultValue = "") Long customerId) throws UnavailableProductException, CustomerIdNullException, NotEnoughProductsException {
        return orderService.createOrder(orderCreateDto, customerId);
    }

    @PatchMapping("/order/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderUpdateDto updateOrder(@Valid @PathVariable("orderId") UUID orderId, @Valid @RequestBody List<ProductOrderDto> products,
                                      @RequestHeader(value = "customerId", defaultValue = "") Long customerId) throws UpdateOrderException, UnavailableProductException, CustomerIdNullException, NotEnoughProductsException, WrongCustomerIdException {
        return orderService.updateOrderById(orderId, products, customerId);
    }

    @GetMapping("/order/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderGetResponseDto getOrder(@PathVariable("orderId") UUID orderId, @RequestHeader(value = "customerId", defaultValue = "") Long customerId) throws CustomerIdNullException, WrongCustomerIdException {
        return orderService.getOrderById(orderId, customerId);
    }

    @DeleteMapping("/order/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteOrder(@PathVariable("orderId") UUID orderId, @RequestHeader(value = "customerId", defaultValue = "") Long customerId) throws UpdateOrderException, CustomerIdNullException, WrongCustomerIdException {
        orderService.deleteOrderById(orderId, customerId);
    }

    @PatchMapping("/order/{orderId}/status")
    @ResponseStatus(HttpStatus.OK)
    public StatusResponseDto updateOrderStatus(@Valid @PathVariable("orderId") UUID orderId, @Valid @RequestBody StatusResponseDto status, @RequestHeader(value = "customerId", defaultValue = "") Long customerId) throws CustomerIdNullException, WrongCustomerIdException {
        return orderService.setOrderStatusById(orderId, status, customerId);
    }
}


