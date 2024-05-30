package com.warehousesystem.app.kafka.handler;

import com.warehousesystem.app.errorhandler.Exception.*;
import com.warehousesystem.app.kafka.event.EventSource;

public interface EventHandler <T extends EventSource> {
    boolean canHandle(EventSource eventSource);
    String handleEvent(T eventSource) throws UnavailableProductException, CustomerIdNullException, NotEnoughProductsException, UpdateOrderException, WrongCustomerIdException;
}
