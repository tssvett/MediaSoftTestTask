package com.warehousesystem.app.kafka.handler;

import com.warehousesystem.app.enums.Event;
import com.warehousesystem.app.errorhandler.Exception.*;
import com.warehousesystem.app.kafka.event.EventSource;
import com.warehousesystem.app.kafka.event.impl.UpdateOrderEventImpl;
import com.warehousesystem.app.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateOrderHandlerImpl implements EventHandler<UpdateOrderEventImpl> {

    private final OrderService orderService;

    @Override
    public boolean canHandle(EventSource eventSource) {
        if (Objects.isNull(eventSource)) {
            log.error("EventSource is null");
            return false;
        }
        return Event.UPDATE_ORDER.equals(eventSource.getEvent());
    }

    @Override
    public String handleEvent(UpdateOrderEventImpl eventSource) throws UnavailableProductException, CustomerIdNullException, NotEnoughProductsException, UpdateOrderException, WrongCustomerIdException {
        if (Objects.isNull(eventSource)) {
            log.error("EventSource is null");
            return null;
        }
        orderService.update(eventSource.getOrderId(), eventSource.getOrder().getProducts(), eventSource.getCustomerId());
        log.info("Event handle: {}", eventSource);
        return eventSource.getEvent().name();
    }
}
