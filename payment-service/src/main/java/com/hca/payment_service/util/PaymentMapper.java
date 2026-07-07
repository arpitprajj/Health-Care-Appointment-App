package com.hca.payment_service.util;

import com.hca.payment_service.dto.PaymentResponse;
import com.hca.payment_service.entity.Payment;

public class PaymentMapper {

    public static PaymentResponse toDto(Payment payment) {
        if (payment == null) {
            return null;
        }

        PaymentResponse response = new PaymentResponse();
        response.setPaymentId(payment.getPaymentId());
        response.setAppointmentId(payment.getAppointmentId());
        response.setAmount(payment.getAmount());
        response.setPaymentMethod(payment.getPaymentMethod());
        response.setPaymentStatus(payment.getPaymentStatus());
        response.setTransactionId(payment.getTransactionId());

        return response;
    }
}
