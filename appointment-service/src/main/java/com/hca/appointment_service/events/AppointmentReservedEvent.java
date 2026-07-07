package com.hca.appointment_service.events;

import com.hca.appointment_service.enums.EventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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