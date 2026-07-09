package com.hca.payment_service.dto;

import com.hca.payment_service.enums.PaymentMethod;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateOrderRequest {

    private UUID appointmentId;

    private PaymentMethod paymentMethod;

}