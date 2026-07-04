package com.hca.payment_service.service;

import com.hca.payment_service.dto.PaymentRequest;
import com.hca.payment_service.dto.PaymentResponse;

import java.util.UUID;

public interface PaymentService {

    PaymentResponse makePayment(
            PaymentRequest request);

    PaymentResponse getPayment(
            UUID paymentId);

}