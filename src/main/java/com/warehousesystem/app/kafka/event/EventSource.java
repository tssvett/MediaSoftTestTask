package com.warehousesystem.app.kafka.event;

import com.warehousesystem.app.enums.Event;

public interface EventSource {

    Event getEvent();
}
