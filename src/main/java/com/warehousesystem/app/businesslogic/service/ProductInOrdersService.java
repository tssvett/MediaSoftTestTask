package com.warehousesystem.app.businesslogic.service;

import com.warehousesystem.app.businesslogic.integration.account.AccountServiceClient;
import com.warehousesystem.app.businesslogic.integration.crm.CrmServiceClient;
import com.warehousesystem.app.dto.customer.CustomerInfo;
import com.warehousesystem.app.dto.order.OrderInfo;
import com.warehousesystem.app.model.Order;
import com.warehousesystem.app.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static java.util.stream.Collectors.*;
import static org.springframework.data.util.Pair.of;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductInOrdersService {

    private final AccountServiceClient accountServiceClient;
    private final CrmServiceClient crmServiceClient;
    private final OrderRepository orderRepository;

    public Map<UUID, List<OrderInfo>> getOrderInfo() throws ExecutionException, InterruptedException {
        List<Order> confirmedOrders = orderRepository.findAllByStatus();
        List<String> logins = confirmedOrders.stream().map(order -> order.getCustomer().getLogin()).distinct().toList();
        Map<String, String> accounts = accountServiceClient.getAccountsByLogins(logins).get();
        Map<String, String> inns = crmServiceClient.getInnsByLogins(logins).get();
        log.info("Get accounts by logins: {}", logins);
        log.info("Get accounts: {}", accounts);
        log.info("Get inns: {}", inns);
        return confirmedOrders.stream().flatMap(order -> order.getPreparedProducts()
                        .stream().map(preparedProduct -> of(preparedProduct.getPk().getProductId(),
                                OrderInfo.builder().id(order.getId())
                                        .customer(CustomerInfo.builder()
                                                .id(order.getCustomer().getId())
                                                .accountNumber(accounts.get(order.getCustomer().getLogin()))
                                                .email(order.getCustomer().getEmail())
                                                .inn(inns.get(order.getCustomer().getLogin()))
                                                .build())
                                        .status(order.getStatus())
                                        .deliveryAddress(order.getDeliveryAddress())
                                        .quantity(preparedProduct.getQuantity()).build())))
                .collect(groupingBy(Pair::getFirst, mapping(Pair::getSecond, toList())));
    }
}
