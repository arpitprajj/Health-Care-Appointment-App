package com.hca.notification_service.consumer;

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

    @KafkaListener(
            topics = "appointment-reserved",
            groupId = "notification-group")
    public void consumeReserved(
            AppointmentReservedEvent event) {

        log.info("Reserved Event Received");

        notificationService.handleReservedAppointment(event);
    }

    @KafkaListener(
            topics = "appointment-confirmed",
            groupId = "notification-group")
    public void consumeConfirmed(
            AppointmentConfirmedEvent event) {

        log.info("Confirmed Event Received");

        notificationService.handleConfirmedAppointment(event);
    }
}