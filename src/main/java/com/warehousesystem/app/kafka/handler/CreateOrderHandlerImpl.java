package com.warehousesystem.app.kafka.handler;

import com.warehousesystem.app.dto.order.OrderCreateDto;
import com.warehousesystem.app.enums.Event;
import com.warehousesystem.app.errorhandler.Exception.CustomerIdNullException;
import com.warehousesystem.app.errorhandler.Exception.NotEnoughProductsException;
import com.warehousesystem.app.errorhandler.Exception.UnavailableProductException;
import com.warehousesystem.app.kafka.event.EventSource;
import com.warehousesystem.app.kafka.event.impl.CreateOrderEventImpl;
import com.warehousesystem.app.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateOrderHandlerImpl implements EventHandler<CreateOrderEventImpl> {

    private final OrderService orderService;

    @Override
    public boolean canHandle(EventSource eventSource) {
        if (Objects.isNull(eventSource)) {
            log.error("EventSource is null");
            return false;
        }
        return Event.CREATE_ORDER.equals(eventSource.getEvent());
    }

    @Override
    public String handleEvent(CreateOrderEventImpl eventSource) throws UnavailableProductException, CustomerIdNullException, NotEnoughProductsException {
        if (Objects.isNull(eventSource)) {
            log.error("EventSource is null");
            return null;
        }
        orderService.create(OrderCreateDto.builder()
                .deliveryAddress(eventSource.getOrder().getDeliveryAddress())
                .products(eventSource.getOrder().getProducts())
                .build(),
                eventSource.getCustomerId());
        log.info("Event handle: {}", eventSource);
        return eventSource.getEvent().name();
    }
}
