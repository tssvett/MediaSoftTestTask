package com.warehousesystem.app.kafka.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.warehousesystem.app.kafka.event.impl.CreateOrderEventImpl;
import com.warehousesystem.app.kafka.event.impl.DeleteOrderEventImpl;
import com.warehousesystem.app.kafka.event.impl.UpdateOrderEventImpl;
import com.warehousesystem.app.kafka.event.impl.UpdateStatusOrderEventImpl;


@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        visible = true,
        include = JsonTypeInfo.As.PROPERTY,
        property = "event"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreateOrderEventImpl.class, name = "CREATE_ORDER"),
        @JsonSubTypes.Type(value = UpdateOrderEventImpl.class, name = "UPDATE_ORDER"),
        @JsonSubTypes.Type(value = DeleteOrderEventImpl.class, name = "DELETE_ORDER"),
        @JsonSubTypes.Type(value = UpdateStatusOrderEventImpl.class, name = "UPDATE_ORDER_STATUS")
})
public interface Event extends EventSource {
}