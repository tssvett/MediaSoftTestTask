package com.warehousesystem.app.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.warehousesystem.app.errorhandler.Exception.*;
import com.warehousesystem.app.kafka.event.Event;
import com.warehousesystem.app.kafka.event.EventSource;
import com.warehousesystem.app.kafka.handler.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "app", name = "kafka.enabled", matchIfMissing = false)
public class Consumer {

    private final Set<EventHandler<EventSource>> eventHandlers;

    @KafkaListener(topics = "test_topic", groupId = "group1", containerFactory = "kafkaListenerContainerFactoryByte")
    public void listenGroupTopic2(byte[] message) throws IOException, UpdateOrderException, UnavailableProductException, CustomerIdNullException, NotEnoughProductsException, WrongCustomerIdException {
        log.info("Receive message: {}", message);
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            final Event eventSource = objectMapper.readValue(message, Event.class);
            log.info("EventSource: {}", eventSource);

            eventHandlers.stream()
                    .filter(eventSourceEventHandler -> eventSourceEventHandler.canHandle(eventSource))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Handler for eventsource not found"))
                    .handleEvent(eventSource);

        } catch (JsonProcessingException e) {
            log.error("Couldn't parse message: {}; exception: ", message, e);
        }
    }
}