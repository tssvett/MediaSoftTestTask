package com.warehousesystem.app.controller;

import com.warehousesystem.app.businesslogic.integration.orchestrator.OrchestratorServiceClient;
import com.warehousesystem.app.businesslogic.service.ProductInOrdersService;
import com.warehousesystem.app.dto.delivery.DeliveryDto;
import com.warehousesystem.app.dto.order.OrderCreateDto;
import com.warehousesystem.app.dto.order.OrderGetResponseDto;
import com.warehousesystem.app.dto.order.OrderInfo;
import com.warehousesystem.app.dto.order.OrderUpdateDto;
import com.warehousesystem.app.dto.product.ProductOrderDto;
import com.warehousesystem.app.dto.status.StatusResponseDto;
import com.warehousesystem.app.service.OrderService;
import dev.tssvett.handler.exception.CustomerIdNullException;
import dev.tssvett.handler.exception.NotEnoughProductsException;
import dev.tssvett.handler.exception.UnavailableProductException;
import dev.tssvett.handler.exception.UpdateOrderException;
import dev.tssvett.handler.exception.WrongCustomerIdException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
@Validated
@RequiredArgsConstructor
@CrossOrigin
public class OrderController {

    private final OrderService orderService;
    private final ProductInOrdersService productInOrdersService;



    @PostMapping("/order")
    public ResponseEntity<UUID> createOrder(@Valid @RequestBody OrderCreateDto orderCreateDto, @RequestHeader(value = "customerId", defaultValue = "") Long customerId) throws CustomerIdNullException, NotEnoughProductsException, UnavailableProductException {
        UUID orderId = orderService.create(orderCreateDto, customerId);
        return new ResponseEntity<>(orderId, HttpStatus.OK);
    }

    @PatchMapping("/order/{orderId}")
    public ResponseEntity<OrderUpdateDto> updateOrder(@Valid @PathVariable("orderId") UUID orderId, @Valid @RequestBody List<ProductOrderDto> products, @RequestHeader(value = "customerId", defaultValue = "") Long customerId) throws CustomerIdNullException, NotEnoughProductsException, UnavailableProductException, UpdateOrderException, WrongCustomerIdException {
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

    @PatchMapping("/order/{orderId}/delivery")
    public ResponseEntity<DeliveryDto> updateOrderDelivery(@Valid @PathVariable("orderId") UUID orderId, @Valid @RequestBody DeliveryDto delivery, @RequestHeader(value = "customerId", defaultValue = "") Long customerId) throws CustomerIdNullException, WrongCustomerIdException {
        DeliveryDto deliveryDto = orderService.setDelivery(orderId, delivery, customerId);
        return new ResponseEntity<>(deliveryDto, HttpStatus.OK);
    }

    @GetMapping("/product/orders")
    public ResponseEntity<Map<UUID, List<OrderInfo>>> getOrders() throws ExecutionException, InterruptedException {
        Map<UUID, List<OrderInfo>> orders = productInOrdersService.getOrderInfo();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PostMapping("/order/{orderId}/confirm")
    public ResponseEntity<UUID> confirmOrder(@Valid @PathVariable("orderId") UUID orderId, @RequestHeader(value = "customerId", defaultValue = "") Long customerId) throws CustomerIdNullException, WrongCustomerIdException, ExecutionException, InterruptedException {
        return new ResponseEntity<>(orderService.startOrderConfirmProcess(orderId, customerId), HttpStatus.OK);
    }
}


