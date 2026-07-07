package com.hca.notification_service.dto;

import com.hca.notification_service.enums.EventType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class AppointmentConfirmedEvent {

    private UUID appointmentId;

    private String patientId;

    private String patientEmail;

    private String patientName;

    private String doctorId;

    private String doctorEmail;

    private String doctorName;

    private LocalDateTime appointmentTime;

    private EventType eventType;

}