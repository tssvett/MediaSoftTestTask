package com.warehousesystem.app.service.impl;

import com.warehousesystem.app.dto.order.OrderCreateDto;
import com.warehousesystem.app.dto.order.OrderGetResponseDto;
import com.warehousesystem.app.dto.order.OrderUpdateDto;
import com.warehousesystem.app.dto.product.ProductOrderDto;
import com.warehousesystem.app.dto.status.StatusResponseDto;
import com.warehousesystem.app.enums.Status;
import com.warehousesystem.app.handler.exception.CustomerIdNullException;
import com.warehousesystem.app.handler.exception.NotEnoughProductsException;
import com.warehousesystem.app.handler.exception.OrderIdNullException;
import com.warehousesystem.app.handler.exception.UnavailableProductException;
import com.warehousesystem.app.handler.exception.UpdateOrderException;
import com.warehousesystem.app.handler.exception.WrongCustomerIdException;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final PreparedProductRepository preparedOrderRepository;
    private final MappingUtils mappingUtils;

    @Override
    public UUID createOrder(OrderCreateDto orderCreateDto, Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerIdNullException("CustomerId Not found!"));

        Order order = Order.builder()
                .customerId(customer)
                .deliveryAddress(orderCreateDto.getDeliveryAddress())
                .status(Status.CREATED)
                .build();

        List<UUID> productIds = orderCreateDto.getProducts()
                .stream()
                .map(ProductOrderDto::getId)
                .toList();

        Map<UUID, Product> productsMap = productRepository.findAllById(productIds)
                .stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));

        List<PreparedProduct> preparedProducts = orderCreateDto.getProducts()
                .stream()
                .map((productOrderDto -> createPreparedProduct(productOrderDto, productsMap, order)))
                .toList();

        order.setPreparedProducts(preparedProducts);
        Order savedOrder = orderRepository.save(order);
        preparedOrderRepository.saveAll(preparedProducts);
        log.info("Order created {}", savedOrder);
        log.info("Prepared products {}", preparedProducts);

        return savedOrder.getId();
    }

    @Override
    public OrderUpdateDto updateOrderById(UUID orderId, List<ProductOrderDto> products, Long customerId) {
        if (customerId == null) {
            throw new CustomerIdNullException("CustomerId is null!");
        }
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderIdNullException("Order Not found!"));
        if (order.getCustomerId().getId() != customerId) {
            throw new WrongCustomerIdException("Wrong CustomerId");
        }
        if (!order.getStatus().equals(Status.CREATED)) {
            throw new UpdateOrderException("Order with id " + orderId + " is not created because status is not CREATED");
        }
        List<UUID> productIds = products
                .stream()
                .map(ProductOrderDto::getId)
                .toList();
        Map<UUID, Product> productsMap = productRepository.findAllById(productIds).stream().collect(Collectors.toMap(Product::getId, Function.identity()));
        List<PreparedProduct> preparedProducts = preparedOrderRepository.findAllByOrderId(orderId);
        List<ProductOrderDto> updatedPreparedProducts = products
                .stream()
                .map(productOrderDto -> updatePreparedProduct(preparedProducts, productOrderDto, productsMap, order))
                .toList();
        log.info("Updated Prepared products {}", updatedPreparedProducts);
        preparedOrderRepository.saveAll(preparedProducts);
        return OrderUpdateDto.builder()
                .deliveryAddress(order.getDeliveryAddress())
                .products(preparedProducts.stream()
                        .map(mappingUtils::mapPreparedProductToProductOrderDto)
                        .toList())
                .build();
    }

    @Override
    public OrderGetResponseDto getOrderById(UUID orderId, Long customerId) {
        if (customerId == null) {
            throw new CustomerIdNullException("CustomerId is null!");
        }
        Order order = orderRepository.findByIdPreparedProducts(orderId).orElseThrow(() -> new OrderIdNullException("Order Not found!"));
        if (order.getCustomerId().getId() != customerId) {
            throw new WrongCustomerIdException("Wrong CustomerId");
        }
        return OrderGetResponseDto.builder()
                .id(order.getId())
                .products(order.getPreparedProducts()
                        .stream()
                        .map(mappingUtils::mapPreparedProductToProductGetResponseDto)
                        .toList())
                .totalPrice(calculateTotalPrice(order.getPreparedProducts()))
                .build();
    }

    @Override
    public UUID deleteOrderById(UUID orderId, Long customerId) {
        if (customerId == null) {
            throw new CustomerIdNullException("CustomerId is null!");
        }
        Order order = orderRepository.findByIdPreparedProducts(orderId).orElseThrow(() -> new OrderIdNullException("Order Not found!"));
        if (order.getCustomerId().getId() != customerId) {
            throw new WrongCustomerIdException("Wrong CustomerId");
        }
        if (!order.getStatus().equals(Status.CREATED)) {
            throw new UpdateOrderException("Cannot delete order with id " + orderId);
        }
        for (PreparedProduct preparedProduct : order.getPreparedProducts()) {
            Product product = preparedProduct.getProduct();
            product.setQuantity(product.getQuantity().add(preparedProduct.getQuantity()));
            product.setAvailable(false);
            productRepository.save(product);
        }
        order.setStatus(Status.CANCELLED);
        orderRepository.save(order);

        return order.getId();
    }

    @Override
    public StatusResponseDto setOrderStatusById(UUID orderId, StatusResponseDto status, Long customerId) {
        if (customerId == null) {
            throw new CustomerIdNullException("CustomerId is null!");
        }
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderIdNullException("Order Not found!"));
        if (order.getCustomerId().getId() != customerId) {
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

    private PreparedProduct createPreparedProduct(ProductOrderDto productOrderDto, Map<UUID, Product> productsMap, Order order) {
        Product product = productsMap.get(productOrderDto.getId());
        checkProductForValidity(product, productOrderDto);
        product.setQuantity(product.getQuantity().subtract(product.getQuantity()));
        productRepository.save(product);

        return PreparedProduct.builder()
                .pk(new PreparedOrderPK(product.getId(), order.getId()))
                .order(order)
                .product(product)
                .price(product.getPrice())
                .quantity(productOrderDto.getQuantity())
                .build();
    }

    private ProductOrderDto updatePreparedProduct(List<PreparedProduct> preparedProducts, ProductOrderDto productOrderDto,
                                                  Map<UUID, Product> productsMap, Order order) {
        Product product = productsMap.get(productOrderDto.getId());
        checkProductForValidity(product, productOrderDto);
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

        return productOrderDto;
    }
    private void checkProductForValidity(Product product, ProductOrderDto productOrderDto) {
        if (product == null) {
            throw new UnavailableProductException("Product with id " + productOrderDto.getId() + " is not available");
        }
        if (product.getQuantity().compareTo(productOrderDto.getQuantity()) < 0) {
            throw new NotEnoughProductsException("Product with id " + productOrderDto.getId() + " are not in stock in such quantities...");
        }
        if (!product.isAvailable()) {
            throw new UnavailableProductException("Product with id " + product.getId() + " is not available");
        }
    }
}
