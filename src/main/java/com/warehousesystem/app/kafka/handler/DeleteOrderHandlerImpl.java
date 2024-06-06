package com.warehousesystem.app.kafka.handler;

import com.warehousesystem.app.enums.Event;
import com.warehousesystem.app.kafka.event.EventSource;
import com.warehousesystem.app.kafka.event.impl.DeleteOrderEventImpl;
import com.warehousesystem.app.service.OrderService;
import dev.tssvett.handler.exception.CustomerIdNullException;
import dev.tssvett.handler.exception.NotEnoughProductsException;
import dev.tssvett.handler.exception.UnavailableProductException;
import dev.tssvett.handler.exception.UpdateOrderException;
import dev.tssvett.handler.exception.WrongCustomerIdException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeleteOrderHandlerImpl implements EventHandler<DeleteOrderEventImpl> {

    private final OrderService orderService;

    @Override
    public boolean canHandle(EventSource eventSource) {
        if (Objects.isNull(eventSource)) {
            log.error("EventSource is null");
            return false;
        }
        return Event.DELETE_ORDER.equals(eventSource.getEvent());
    }

    @Override
    public String handleEvent(DeleteOrderEventImpl eventSource) throws UnavailableProductException, CustomerIdNullException, NotEnoughProductsException, UpdateOrderException, WrongCustomerIdException {
        if (Objects.isNull(eventSource)) {
            log.error("EventSource is null");
            return null;
        }
        orderService.delete(eventSource.getOrderId(), eventSource.getCustomerId());
        log.info("Event handle: {}", eventSource);
        return eventSource.getEvent().name();
    }
}
