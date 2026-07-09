package com.hca.payment_service.entity;

import com.hca.payment_service.enums.PaymentMethod;
import com.hca.payment_service.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "payments")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID paymentId;

    UUID appointmentId;

    BigDecimal amount;

    PaymentStatus paymentStatus;

    PaymentMethod paymentMethod;

    String razorpayOrderId;

    String razorpayPaymentId;

    String razorpaySignature;

    String currency;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;
}