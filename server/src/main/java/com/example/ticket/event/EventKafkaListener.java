package com.example.ticket.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(
        prefix = "application.kafka",
        value = {"enabled", "event-listener-enabled"},
        havingValue = "true"
)
@KafkaListener(
        topics = "#{kafkaEnvironment}.event.v1",
        properties = "auto.offset.reset:latest",
        groupId = "#{kafkaEnvironment}-event-events-listener-v1"
)
public class EventKafkaListener {

    private final EventService eventService;

    @KafkaHandler
    public void handle(EventCreated event){eventService.handleEventCreated(event);}


    @KafkaHandler(isDefault = true)
    public void handle(Object event){log.debug("Ignore unhandled event: {}", event.getClass().getName());}
}