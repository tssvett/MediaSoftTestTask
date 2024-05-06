package com.warehousesystem.app.service.impl;

import com.warehousesystem.app.dto.order.OrderCreateDto;
import com.warehousesystem.app.dto.product.ProductOrderDto;
import com.warehousesystem.app.enums.Status;
import com.warehousesystem.app.handler.Exception.CustomerIdNullException;
import com.warehousesystem.app.model.Order;
import com.warehousesystem.app.model.PreparedProduct;
import com.warehousesystem.app.model.Product;
import com.warehousesystem.app.repository.CustomerRepository;
import com.warehousesystem.app.repository.OrderRepository;
import com.warehousesystem.app.repository.ProductRepository;
import com.warehousesystem.app.service.OrderService;
import com.warehousesystem.app.service.ProductService;
import com.warehousesystem.app.utils.MappingUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MappingUtils mappingUtils;
    @Override
    public UUID create(OrderCreateDto orderCreateDto, Long customerId) throws CustomerIdNullException {
        if (customerId == null) {
            throw new CustomerIdNullException("CustomerId is null!");
        }
        if (customerRepository.findById(customerId).isEmpty()) {
            throw new CustomerIdNullException("Customer not found!");
        }
        List<PreparedProduct> preparedProducts = orderCreateDto.getProducts().stream().map(productOrderDto -> {
            Product product = productRepository.getReferenceById(productOrderDto.getId());
            if (product.getQuantity().compareTo(productOrderDto.getQuantity()) < 0) {
                throw new CustomerIdNullException("Not enough products in stock!");
            }
            return PreparedProduct.builder()
                    .price(product.getPrice())
                    .quantity(product.getQuantity())
                    .build();
        }).toList();



        Order order = Order.builder()
                .customerId(customerRepository.getReferenceById(customerId))
                .deliveryAddress(orderCreateDto.getDeliveryAddress())
                .status(Status.CREATED)
                .build();

        log.info("Order created {}", order);
        log.info("Prepared products {}", preparedProducts);
        return UUID.randomUUID();
    }
}
