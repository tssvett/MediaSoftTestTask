package com.warehousesystem.app.kafka.handler;

import com.warehousesystem.app.kafka.event.EventSource;
import dev.tssvett.handler.exception.CustomerIdNullException;
import dev.tssvett.handler.exception.NotEnoughProductsException;
import dev.tssvett.handler.exception.UnavailableProductException;
import dev.tssvett.handler.exception.UpdateOrderException;
import dev.tssvett.handler.exception.WrongCustomerIdException;

public interface EventHandler<T extends EventSource> {
    boolean canHandle(EventSource eventSource);

    String handleEvent(T eventSource) throws UnavailableProductException, CustomerIdNullException, NotEnoughProductsException, UpdateOrderException, WrongCustomerIdException;
}
