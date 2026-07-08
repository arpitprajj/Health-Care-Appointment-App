package com.hca.notification_service.service.impl;

import com.hca.notification_service.dto.AppointmentConfirmedEvent;
import com.hca.notification_service.dto.AppointmentReservedEvent;
import com.hca.notification_service.service.EmailTemplateService;
import org.springframework.stereotype.Service;

@Service
public class EmailTemplateServiceImpl
        implements EmailTemplateService {

    @Override
    public String getReservationSubject() {

        return "Appointment Reserved";
    }

    @Override
    public String getConfirmationSubject() {

        return "Appointment Confirmed";
    }

    @Override
    public String buildReservationEmail(
            AppointmentReservedEvent event) {

        return """
                Dear %s,

                Your appointment has been reserved successfully.

                Doctor : %s

                Time : %s

                Please complete your payment within 5 minutes.

                Thank you.
                """
                .formatted(
                        event.getPatientName(),
                        event.getAppointmentId(),
                        event.getAppointmentTime());
    }

    @Override
    public String buildConfirmationEmail(
            AppointmentConfirmedEvent event) {

        return """
                Dear %s,

                Your appointment has been confirmed.

                Doctor : %s

                Time : %s

                Your consultation is now booked.

                Thank you.
                """
                .formatted(
                        event.getPatientName(),
                        event.getDoctorName(),
                        event.getAppointmentTime());
    }
}