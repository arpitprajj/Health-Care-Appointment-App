package com.hca.notification_service.service.impl;

import com.hca.notification_service.dto.AppointmentConfirmedEvent;
import com.hca.notification_service.dto.AppointmentReservedEvent;
import com.hca.notification_service.entity.Notification;
import com.hca.notification_service.enums.EventType;
import com.hca.notification_service.enums.NotificationStatus;
import com.hca.notification_service.repository.NotificationRepository;
import com.hca.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl
        implements NotificationService {

    private final NotificationRepository repository;

    @Override
    public void handleReservedAppointment(
            AppointmentReservedEvent event) {

        log.info("Appointment Reserved Event Received : {}",
                event.getAppointmentId());

        Notification notification =
                Notification.builder()
                        .appointmentId(event.getAppointmentId())
                        .recipientEmail(event.getPatientEmail())
                        .subject("Appointment Reserved")
                        .message(
                                "Your appointment has been reserved. Please complete payment.")
                        .eventType(EventType.APPOINTMENT_RESERVED)
                        .notificationStatus(NotificationStatus.PENDING)
                        .createdAt(LocalDateTime.now())
                        .build();

        repository.save(notification);
    }

    @Override
    public void handleConfirmedAppointment(
            AppointmentConfirmedEvent event) {

        log.info("Appointment Confirmed Event Received : {}",
                event.getAppointmentId());

        Notification notification =
                Notification.builder()
                        .appointmentId(event.getAppointmentId())
                        .recipientEmail(event.getPatientEmail())
                        .subject("Appointment Confirmed")
                        .message(
                                "Your appointment has been confirmed.")
                        .eventType(EventType.APPOINTMENT_CONFIRMED)
                        .notificationStatus(NotificationStatus.PENDING)
                        .createdAt(LocalDateTime.now())
                        .build();

        repository.save(notification);
    }
}