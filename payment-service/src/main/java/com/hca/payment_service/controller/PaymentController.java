package com.hca.payment_service.controller;

import com.hca.payment_service.dto.*;
import com.hca.payment_service.service.PaymentService;
import com.hca.payment_service.service.RazorpayService;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService service;
    private final RazorpayService razorpayService;

//    @PostMapping
//    public ResponseEntity<PaymentResponse> pay(
//            @RequestBody PaymentRequest request) {
//
//        return ResponseEntity.ok(
//                service.makePayment(request));
//    }
        @PostMapping("/create-order")
        public ResponseEntity<CreateOrderResponse>
        createOrder(@RequestBody CreateOrderRequest request) throws RazorpayException {

    return ResponseEntity.ok(
            service.createOrder(
                    request));
}

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentResponse> getPayment(
            @PathVariable UUID paymentId) {

        return ResponseEntity.ok(
                service.getPayment(paymentId));
    }
    @PostMapping("/verify")
    public ResponseEntity<PaymentResponse>
    verify(

            @RequestBody VerifyPaymentRequest request) {

        return ResponseEntity.ok(
                service.verifyPayment(
                        request));
    }
}