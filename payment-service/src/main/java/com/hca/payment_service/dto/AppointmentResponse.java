package com.hca.payment_service.dto;

import com.hca.payment_service.enums.AppointmentStatus;
import com.hca.payment_service.enums.PaymentStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class AppointmentResponse {

    private UUID appointmentId;

    private String patientId;

    private String doctorId;

    private UUID slotId;
    private BigDecimal consultationFee;

    private AppointmentStatus appointmentStatus;

    private PaymentStatus paymentStatus;

    private LocalDateTime appointmentTime;
}
