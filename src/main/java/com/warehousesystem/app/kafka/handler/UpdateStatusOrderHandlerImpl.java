package com.warehousesystem.app.kafka.handler;

import com.warehousesystem.app.enums.Event;
import com.warehousesystem.app.errorhandler.Exception.*;
import com.warehousesystem.app.kafka.event.EventSource;
import com.warehousesystem.app.kafka.event.impl.DeleteOrderEventImpl;
import com.warehousesystem.app.kafka.event.impl.UpdateStatusOrderEventImpl;
import com.warehousesystem.app.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateStatusOrderHandlerImpl implements EventHandler<UpdateStatusOrderEventImpl> {

    private final OrderService orderService;

    @Override
    public boolean canHandle(EventSource eventSource) {
        if (Objects.isNull(eventSource)) {
            log.error("EventSource is null");
            return false;
        }
        return Event.UPDATE_ORDER_STATUS.equals(eventSource.getEvent());
    }

    @Override
    public String handleEvent(UpdateStatusOrderEventImpl eventSource) throws UnavailableProductException, CustomerIdNullException, NotEnoughProductsException, UpdateOrderException, WrongCustomerIdException {
        if (Objects.isNull(eventSource)) {
            log.error("EventSource is null");
            return null;
        }
        orderService.setStatus(eventSource.getOrderId(), eventSource.getStatus(), eventSource.getCustomerId());
        log.info("Event handle: {}", eventSource);
        return eventSource.getEvent().name();
    }
}

