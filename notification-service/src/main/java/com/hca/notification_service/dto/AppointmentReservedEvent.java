package com.hca.notification_service.dto;

import com.hca.notification_service.enums.EventType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class AppointmentReservedEvent {

    private UUID appointmentId;

    private String patientId;

    private String patientEmail;

    private String patientName;

    private String doctorId;

    private LocalDateTime appointmentTime;

    private BigDecimal consultationFee;

    private EventType eventType;

}