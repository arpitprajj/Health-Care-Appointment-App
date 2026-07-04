package com.hca.payment_service.dto;

import com.hca.payment_service.enums.PaymentMethod;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class PaymentRequest {

    private UUID appointmentId;

    private BigDecimal amount;

    private PaymentMethod paymentMethod;

}