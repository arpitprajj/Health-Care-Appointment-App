package com.hca.notification_service.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hca.notification_service.dto.AppointmentConfirmedEvent;
import com.hca.notification_service.dto.AppointmentReservedEvent;
import com.hca.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AppointmentEventConsumer {

    private final NotificationService notificationService;
    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics = "appointment-reserved",
            groupId = "notification-group")
    public void consumeReserved(
            String json) throws JsonProcessingException {
        AppointmentReservedEvent event=objectMapper.readValue(json, AppointmentReservedEvent.class);
        log.info("==========================Reserved Event Received=============================================");

        notificationService.handleReservedAppointment(event);
    }

    @KafkaListener(
            topics = "appointment-confirmed",
            groupId = "notification-group")
    public void consumeConfirmed(
            String json) throws JsonProcessingException {
        AppointmentConfirmedEvent event=objectMapper.readValue(json, AppointmentConfirmedEvent.class);
        log.info("=====================Confirmed Event Received=================");

        notificationService.handleConfirmedAppointment(event);
    }
}