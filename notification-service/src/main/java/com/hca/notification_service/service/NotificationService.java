package com.hca.notification_service.service;

import com.hca.notification_service.dto.AppointmentConfirmedEvent;
import com.hca.notification_service.dto.AppointmentReservedEvent;

public interface NotificationService {

    void handleReservedAppointment(
            AppointmentReservedEvent event);

    void handleConfirmedAppointment(
            AppointmentConfirmedEvent event);

}