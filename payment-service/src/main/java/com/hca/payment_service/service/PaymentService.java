package com.hca.payment_service.service;

import com.hca.payment_service.dto.*;
import com.razorpay.RazorpayException;

import java.util.UUID;

public interface PaymentService {

    CreateOrderResponse createOrder(
            CreateOrderRequest request) throws RazorpayException;

    PaymentResponse getPayment(
            UUID paymentId);
    PaymentResponse verifyPayment(
            VerifyPaymentRequest request);

}