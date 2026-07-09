package com.hca.payment_service.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class VerifyPaymentRequest {

    private UUID appointmentId;

    private String razorpayOrderId;

    private String razorpayPaymentId;

    private String razorpaySignature;

}