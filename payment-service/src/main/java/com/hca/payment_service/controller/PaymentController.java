package com.hca.payment_service.controller;

import com.hca.payment_service.dto.PaymentRequest;
import com.hca.payment_service.dto.PaymentResponse;
import com.hca.payment_service.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService service;

    @PostMapping
    public ResponseEntity<PaymentResponse> pay(
            @RequestBody PaymentRequest request) {

        return ResponseEntity.ok(
                service.makePayment(request));
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentResponse> getPayment(
            @PathVariable UUID paymentId) {

        return ResponseEntity.ok(
                service.getPayment(paymentId));
    }
}