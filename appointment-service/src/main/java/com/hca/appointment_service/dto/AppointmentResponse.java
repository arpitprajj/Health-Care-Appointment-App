package com.hca.appointment_service.dto;

import com.hca.appointment_service.enums.AppointmentStatus;
import com.hca.appointment_service.enums.PaymentStatus;
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
public class AppointmentResponse {

    private UUID appointmentId;

    private String patientId;

    private String doctorId;

    private UUID slotId;
    private BigDecimal consultationFee;

    private AppointmentStatus appointmentStatus;

    private PaymentStatus paymentStatus;
    private String meetingLink;
    private String notes;

    private LocalDateTime appointmentTime;


}