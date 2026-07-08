package com.hca.notification_service.service;

import com.hca.notification_service.dto.AppointmentConfirmedEvent;
import com.hca.notification_service.dto.AppointmentReservedEvent;

public interface EmailTemplateService {

    String buildReservationEmail(
            AppointmentReservedEvent event);

    String buildConfirmationEmail(
            AppointmentConfirmedEvent event);

    String getReservationSubject();

    String getConfirmationSubject();

}