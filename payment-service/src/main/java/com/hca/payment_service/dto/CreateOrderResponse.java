package com.hca.payment_service.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CreateOrderResponse {

    private UUID paymentId;

    private UUID appointmentId;

    private String razorpayOrderId;

    private String key;

    private Long amount;

    private String currency;

}