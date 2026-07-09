package com.hca.payment_service.service.impl;

import com.hca.payment_service.dto.VerifyPaymentRequest;
import com.hca.payment_service.service.RazorpayService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RazorpayServiceImpl
        implements RazorpayService {

    private final RazorpayClient razorpayClient;
    @Value("${razorpay.key.secret}")
    private String razorpaySecret;

    @Override
    public Order createOrder(
            BigDecimal amount,
            UUID appointmentId)
            throws RazorpayException {

        JSONObject request = new JSONObject();

        request.put(
                "amount",
                amount.multiply(
                        BigDecimal.valueOf(100)));

        request.put(
                "currency",
                "INR");

        request.put(
                "receipt",
                appointmentId.toString());

        return razorpayClient.orders.create(request);
    }

    @Override
    public boolean verifyPaymentSignature(
            VerifyPaymentRequest request) {

        try {

            JSONObject attributes =
                    new JSONObject();

            attributes.put(
                    "razorpay_order_id",
                    request.getRazorpayOrderId());

            attributes.put(
                    "razorpay_payment_id",
                    request.getRazorpayPaymentId());

            attributes.put(
                    "razorpay_signature",
                    request.getRazorpaySignature());

            Utils.verifyPaymentSignature(
                    attributes,
                    razorpaySecret);

            return true;

        } catch (Exception ex) {

            return false;
        }
    }
}