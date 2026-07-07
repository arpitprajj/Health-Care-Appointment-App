package com.hca.appointment_service.events;

import com.hca.appointment_service.enums.EventType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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