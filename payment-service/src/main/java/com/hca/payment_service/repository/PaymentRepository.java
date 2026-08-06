package com.hca.payment_service.repository;

import com.hca.payment_service.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentRepository
        extends JpaRepository<Payment, UUID> {

    Optional<Payment> findByAppointmentId(
            UUID appointmentId);

    Optional<Payment> findTopByAppointmentIdOrderByCreatedAtDesc(UUID appointmentId);

    Optional<Payment> findByRazorpayOrderId(String razorpayOrderId);
}