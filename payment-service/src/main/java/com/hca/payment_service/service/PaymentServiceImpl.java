package com.hca.payment_service.service;

import com.hca.payment_service.dto.AppointmentResponse;
import com.hca.payment_service.dto.PaymentRequest;
import com.hca.payment_service.dto.PaymentResponse;
import com.hca.payment_service.entity.Payment;
import com.hca.payment_service.enums.AppointmentStatus;
import com.hca.payment_service.enums.PaymentStatus;
import com.hca.payment_service.feign.AppointmentClient;
import com.hca.payment_service.repository.PaymentRepository;
import com.hca.payment_service.util.FakePaymentGateway;
import com.hca.payment_service.util.PaymentMapper;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository repository;

    private final AppointmentClient appointmentClient;

    private final FakePaymentGateway paymentGateway;

    @Override
    public PaymentResponse makePayment(
            PaymentRequest request) {

        repository.findByAppointmentId(
                        request.getAppointmentId())
                .ifPresent(payment -> {

                    throw new RuntimeException(
                            "Payment already exists.");
                });

        AppointmentResponse appointment =
                appointmentClient.getAppointment(
                        request.getAppointmentId());

        if (appointment.getAppointmentStatus()
                != AppointmentStatus.PENDING_PAYMENT) {

            throw new RuntimeException(
                    "Appointment is not waiting for payment.");
        }

        Payment payment =
                Payment.builder()
                        .appointmentId(
                                appointment.getAppointmentId())
                        .amount(
                                appointment.getConsultationFee())
                        .paymentMethod(
                                request.getPaymentMethod())
                        .paymentStatus(
                                PaymentStatus.PENDING)
                        .createdAt(
                                LocalDateTime.now())
                        .updatedAt(
                                LocalDateTime.now())
                        .build();

        payment = repository.save(payment);

        boolean success =
                paymentGateway.processPayment();

        if (success) {

            try {

                appointmentClient.confirmAppointment(
                        appointment.getAppointmentId());

                payment.setPaymentStatus(
                        PaymentStatus.SUCCESS);

                payment.setTransactionId(
                        UUID.randomUUID().toString());

            } catch (FeignException ex) {

                payment.setPaymentStatus(
                        PaymentStatus.REFUNDED);

                // Later
                // refundGateway.refund(...)

                throw new RuntimeException(
                        "Payment successful but appointment confirmation failed.",
                        ex);
            }

        } else {

            payment.setPaymentStatus(
                    PaymentStatus.FAILED);

            // Later

            // appointmentClient.paymentFailed()

        }

        payment.setUpdatedAt(
                LocalDateTime.now());

        repository.save(payment);

        return PaymentMapper.toDto(payment);
    }

    @Override
    public PaymentResponse getPayment(
            UUID paymentId) {

        Payment payment =
                repository.findById(paymentId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Payment not found."));

        return PaymentMapper.toDto(payment);
    }

}