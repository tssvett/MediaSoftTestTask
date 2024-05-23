package com.warehousesystem.app.service.impl;

import com.warehousesystem.app.dto.order.OrderCreateDto;
import com.warehousesystem.app.dto.order.OrderGetResponseDto;
import com.warehousesystem.app.dto.order.OrderUpdateDto;
import com.warehousesystem.app.dto.product.ProductOrderDto;
import com.warehousesystem.app.dto.status.StatusResponseDto;
import com.warehousesystem.app.enums.Status;
import com.warehousesystem.app.handler.Exception.*;
import com.warehousesystem.app.model.Customer;
import com.warehousesystem.app.model.Order;
import com.warehousesystem.app.model.PreparedProduct;
import com.warehousesystem.app.model.Product;
import com.warehousesystem.app.model.compositekey.PreparedOrderPK;
import com.warehousesystem.app.repository.CustomerRepository;
import com.warehousesystem.app.repository.OrderRepository;
import com.warehousesystem.app.repository.PreparedProductRepository;
import com.warehousesystem.app.repository.ProductRepository;
import com.warehousesystem.app.service.OrderService;
import com.warehousesystem.app.utils.MappingUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
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
    private PreparedProductRepository preparedOrderRepository;

    @Autowired
    private MappingUtils mappingUtils;

    @Override
    public UUID create(OrderCreateDto orderCreateDto, Long customerId) throws CustomerIdNullException, NotEnoughProductsException, UnavailableProductException {

        if (customerId == null) {
            throw new CustomerIdNullException("CustomerId is null!");
        }

        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerIdNullException("CustomerId Not found!"));
        Order order = Order.builder()
                .customer(customer)
                .deliveryAddress(orderCreateDto.getDeliveryAddress())
                .status(Status.CREATED)
                .build();

        List<PreparedProduct> preparedProducts = new ArrayList<>();
        for (ProductOrderDto productOrderDto : orderCreateDto.getProducts()) {

            Product product = productRepository.findById(productOrderDto.getId()).orElseThrow(() -> new UnavailableProductException("Product Not found!"));
            log.info("Product {}", product.getId());
            if (!product.isAvailable()) {
                throw new UnavailableProductException("Product with id " + productOrderDto.getId() + " is not available");
            }


            if (product.getQuantity().compareTo(productOrderDto.getQuantity()) < 0) {
                throw new NotEnoughProductsException("Product with id " + productOrderDto.getId() + " are not in stock in such quantities...");
            }
            PreparedProduct preparedProduct = PreparedProduct.builder()
                    .pk(new PreparedOrderPK(product.getId(), order.getId()))
                    .order(order)
                    .product(product)
                    .price(product.getPrice())
                    .quantity(productOrderDto.getQuantity())
                    .build();
            preparedProducts.add(preparedProduct);
            product.setQuantity(product.getQuantity().subtract(productOrderDto.getQuantity()));
            productRepository.save(product);
            log.info("Prepared product {}", preparedProduct.getPrice().toString());
        }
        Order savedOrder = orderRepository.save(order);
        preparedOrderRepository.saveAll(preparedProducts);


        log.info("Order created {}", savedOrder);
        log.info("Prepared products {}", preparedProducts);
        return savedOrder.getId();
    }


    @Override
    public OrderUpdateDto update(UUID orderId, List<ProductOrderDto> products, Long customerId) throws CustomerIdNullException, NotEnoughProductsException, UnavailableProductException, UpdateOrderException, WrongCustomerIdException {

        if (customerId == null) {
            throw new CustomerIdNullException("CustomerId is null!");
        }


        Order order = orderRepository.findById(orderId).orElseThrow(() -> new CustomerIdNullException("Order Not found!"));

        if (order.getCustomer().getId() != customerId) {
            throw new WrongCustomerIdException("Wrong CustomerId");

        }
        if (!order.getStatus().equals(Status.CREATED)) {
            throw new UpdateOrderException("Order with id " + orderId + " is not created because status is not CREATED");
        }
        List<PreparedProduct> preparedProducts = preparedOrderRepository.findAllByOrderId(orderId);
        log.info("Prepared products {}", preparedProducts.stream().toList());
        for (ProductOrderDto productOrderDto : products) {

            Product product = productRepository.findById(productOrderDto.getId()).orElseThrow(() -> new UnavailableProductException("Product Not found!"));
            log.info("Product {}", product.getId());
            if (!product.isAvailable()) {
                throw new UnavailableProductException("Product with id " + productOrderDto.getId() + " is not available");
            }


            if (product.getQuantity().compareTo(productOrderDto.getQuantity()) < 0) {
                throw new NotEnoughProductsException("Product with id " + productOrderDto.getId() + " are not in stock in such quantities...");
            }

            if (preparedProducts.stream().noneMatch(preparedProduct -> preparedProduct.getProduct().getId().equals(product.getId()))) {
                PreparedProduct preparedProduct = PreparedProduct.builder()
                        .pk(new PreparedOrderPK(product.getId(), order.getId()))
                        .order(order)
                        .product(product)
                        .price(product.getPrice())
                        .quantity(productOrderDto.getQuantity())
                        .build();
                preparedProducts.add(preparedProduct);
            }

            product.setQuantity(product.getQuantity().subtract(productOrderDto.getQuantity()));
            productRepository.save(product);
        }
        preparedOrderRepository.saveAll(preparedProducts);


        return OrderUpdateDto.builder().deliveryAddress(order.getDeliveryAddress()).products(preparedProducts.stream().map(mappingUtils::mapPreparedProductToProductOrderDto).toList()).build();
    }

    @Override
    public OrderGetResponseDto get(UUID orderId, Long customerId) throws CustomerIdNullException, WrongCustomerIdException {

        if (customerId == null) {
            throw new CustomerIdNullException("CustomerId is null!");
        }


        Order order = orderRepository.findById(orderId).orElseThrow(() -> new CustomerIdNullException("Order Not found!"));
        if (order.getCustomer().getId() != customerId) {
            throw new WrongCustomerIdException("Wrong CustomerId");

        }
        List<PreparedProduct> preparedProducts = preparedOrderRepository.findAllByOrderId(orderId);
        return OrderGetResponseDto.builder()
                .id(order.getId())
                .products(preparedProducts.stream().map(mappingUtils::mapPreparedProductToProductGetResponseDto).toList())
                .totalPrice(calculateTotalPrice(preparedProducts)).build();
    }

    @Override
    public UUID delete(UUID orderId, Long customerId) throws CustomerIdNullException, WrongCustomerIdException, UpdateOrderException {

        if (customerId == null) {
            throw new CustomerIdNullException("CustomerId is null!");
        }

        Order order = orderRepository.findById(orderId).orElseThrow(() -> new CustomerIdNullException("Order Not found!"));

        if (order.getCustomer().getId() != customerId) {
            throw new WrongCustomerIdException("Wrong CustomerId");

        }
        if (!order.getStatus().equals(Status.CREATED)) {
            throw new UpdateOrderException("Cannot delete order with id " + orderId);
        }

        for (PreparedProduct preparedProduct : order.getPreparedProducts()) {
            Product product = preparedProduct.getProduct();
            product.setQuantity(product.getQuantity().add(preparedProduct.getQuantity()));
            productRepository.save(product);
        }
        order.setStatus(Status.CANCELLED);
        orderRepository.save(order);
        return order.getId();
    }

    @Override
    public StatusResponseDto setStatus(UUID orderId, StatusResponseDto status, Long customerId) throws CustomerIdNullException, WrongCustomerIdException {

        if (customerId == null) {
            throw new CustomerIdNullException("CustomerId is null!");
        }

        Order order = orderRepository.findById(orderId).orElseThrow(() -> new CustomerIdNullException("Order Not found!"));

        if (order.getCustomer().getId() != customerId) {
            throw new WrongCustomerIdException("Wrong CustomerId");
        }
        order.setStatus(status.getStatus());
        orderRepository.save(order);
        return StatusResponseDto.builder().status((status.getStatus())).build();
    }

    private BigDecimal calculateTotalPrice(List<PreparedProduct> preparedProducts) {

        BigDecimal totalPrice = BigDecimal.ZERO;
        for (PreparedProduct preparedProduct : preparedProducts) {
            totalPrice = totalPrice.add(preparedProduct.getPrice().multiply(preparedProduct.getQuantity()));
        }
        return totalPrice;
    }


}
