package com.hca.payment_service.service;

import com.hca.payment_service.dto.VerifyPaymentRequest;
import com.razorpay.Order;
import com.razorpay.RazorpayException;

import java.math.BigDecimal;
import java.util.UUID;

public interface RazorpayService {

    Order createOrder(
            BigDecimal amount,
            UUID appointmentId)
            throws RazorpayException;

    boolean verifyPaymentSignature(
            VerifyPaymentRequest request);

}