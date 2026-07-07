package com.hca.notification_service.dto;

import com.hca.notification_service.enums.EventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentEvent {

    private UUID appointmentId;

    private EventType eventType;

    private String patientEmail;

    private String patientName;

    private String doctorEmail;

    private String doctorName;

    private LocalDateTime appointmentTime;

    private String meetingLink;
}