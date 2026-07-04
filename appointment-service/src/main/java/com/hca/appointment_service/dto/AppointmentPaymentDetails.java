package com.hca.appointment_service.dto;

import com.hca.appointment_service.enums.AppointmentStatus;
import com.hca.appointment_service.enums.PaymentStatus;

import java.math.BigDecimal;
import java.util.UUID;

public class AppointmentPaymentDetails {

    UUID appointmentId;

    BigDecimal amount;

    AppointmentStatus appointmentStatus;

    PaymentStatus paymentStatus;

    UUID slotId;
}