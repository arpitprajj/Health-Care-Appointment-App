package com.hca.payment_service.service.impl;

import com.hca.payment_service.config.RazorpayConfig;
import com.hca.payment_service.dto.*;
import com.hca.payment_service.entity.Payment;
import com.hca.payment_service.enums.AppointmentStatus;
import com.hca.payment_service.enums.PaymentStatus;
import com.hca.payment_service.exception.PaymentException;
import com.hca.payment_service.feign.AppointmentClient;
import com.hca.payment_service.repository.PaymentRepository;
import com.hca.payment_service.service.PaymentService;
import com.hca.payment_service.service.RazorpayService;
import com.hca.payment_service.util.FakePaymentGateway;
import com.hca.payment_service.util.PaymentMapper;
import com.razorpay.Order;
import com.razorpay.RazorpayException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository repository;

    private final AppointmentClient appointmentClient;

    private final RazorpayService razorpayService;

    private final RazorpayConfig razorpayConfig;

    private final FakePaymentGateway paymentGateway;

    //@Override
//    public PaymentResponse makePayment(
//            PaymentRequest request) {
//
//        repository.findByAppointmentId(
//                        request.getAppointmentId())
//                .ifPresent(payment -> {
//
//                    throw new RuntimeException(
//                            "Payment already exists.");
//                });
//
//        AppointmentResponse appointment =
//                appointmentClient.getAppointment(
//                        request.getAppointmentId());
//
//        if (appointment.getAppointmentStatus()
//                != AppointmentStatus.PENDING_PAYMENT) {
//
//            throw new RuntimeException(
//                    "Appointment is not waiting for payment.");
//        }
//
//        Payment payment =
//                Payment.builder()
//                        .appointmentId(
//                                appointment.getAppointmentId())
//                        .amount(
//                                appointment.getConsultationFee())
//                        .paymentMethod(
//                                request.getPaymentMethod())
//                        .paymentStatus(
//                                PaymentStatus.PENDING)
//                        .createdAt(
//                                LocalDateTime.now())
//                        .updatedAt(
//                                LocalDateTime.now())
//                        .build();
//
//        payment = repository.save(payment);
//
//        boolean success =
//                paymentGateway.processPayment();
//
//        if (success) {
//
//            try {
//
//                appointmentClient.confirmAppointment(
//                        appointment.getAppointmentId());
//
//                payment.setPaymentStatus(
//                        PaymentStatus.SUCCESS);
//
//                payment.setTransactionId(
//                        UUID.randomUUID().toString());
//
//            } catch (FeignException ex) {
//
//                payment.setPaymentStatus(
//                        PaymentStatus.REFUNDED);
//
//                // Later
//                // refundGateway.refund(...)
//
//                throw new RuntimeException(
//                        "Payment successful but appointment confirmation failed.",
//                        ex);
//            }
//
//        } else {
//
//            payment.setPaymentStatus(
//                    PaymentStatus.FAILED);
//
//            // Later
//
//            // appointmentClient.paymentFailed()
//
//        }
//
//        payment.setUpdatedAt(
//                LocalDateTime.now());
//
//        repository.save(payment);
//
//        return PaymentMapper.toDto(payment);
//    }
    @Override
    @Transactional
    public CreateOrderResponse createOrder(
            CreateOrderRequest request) throws RazorpayException {
        AppointmentResponse appointment;
       try {
           appointment=
                   appointmentClient.getAppointment(
                           request.getAppointmentId());
       }
       catch (FeignException ex){
           throw new PaymentException("There is something issue in appointment service "+ex.getMessage());
       }

        if (appointment.getPaymentStatus()
                == PaymentStatus.SUCCESS) {

            throw new PaymentException(
                    "Payment already completed.");
        }

        Optional<Payment>existPayment=repository.findTopByAppointmentIdOrderByCreatedAtDesc(
                        request.getAppointmentId());
        if(existPayment.isPresent()){
            Payment payment=existPayment.get();
            if(payment.getPaymentStatus()==PaymentStatus.SUCCESS ){
                throw new PaymentException("Payment already completed");
            }
        }

        Order order =
                razorpayService.createOrder(
                        appointment.getConsultationFee(),
                        appointment.getAppointmentId());

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

                        .razorpayOrderId(
                                order.get("id"))

                        .currency(
                                order.get("currency"))

                        .createdAt(
                                LocalDateTime.now())

                        .updatedAt(
                                LocalDateTime.now())

                        .build();

        payment =
                repository.save(payment);

        return CreateOrderResponse
                .builder()

                .paymentId(
                        payment.getPaymentId())

                .razorpayOrderId(
                        payment.getRazorpayOrderId())
                .appointmentId(appointment.getAppointmentId())

                .key(
                        razorpayConfig.getKeyId())

                .currency(
                        payment.getCurrency())

                .amount(
                        payment.getAmount()
                                .multiply(BigDecimal.valueOf(100))
                                .longValue())

                .build();
    }
    @Override
    public PaymentResponse getPayment(
            UUID paymentId) {

        Payment payment =
                repository.findById(paymentId)
                        .orElseThrow(() ->
                                new PaymentException(
                                        "Payment not found."));

        return PaymentMapper.toDto(payment);
    }

    @Override
    @Transactional
    public PaymentResponse verifyPayment(
            VerifyPaymentRequest request) {

        Optional<Payment> paymentExist = repository
                .findByRazorpayOrderId(request.getRazorpayOrderId());
        if(paymentExist.isEmpty()) throw  new PaymentException("RazorPay Order not found");
        Payment payment=paymentExist.get();

        if (!payment.getAppointmentId().equals(request.getAppointmentId())) {
            throw new PaymentException(
                    "Appointment does not match payment.");
        }
//        Payment payment =
//                repository
//                        .findByAppointmentId(
//                                request.getAppointmentId())
//                        .orElseThrow(
//                                () ->
//                                        new RuntimeException(
//                                                "Payment not found"));

        boolean verified =
                razorpayService
                        .verifyPaymentSignature(
                                request);

        if (!verified) {

            payment.setPaymentStatus(
                    PaymentStatus.FAILED);

            repository.save(payment);

            throw new PaymentException(
                    "Payment verification failed.");
        }

        payment.setPaymentStatus(
                PaymentStatus.SUCCESS);

        payment.setRazorpayPaymentId(
                request.getRazorpayPaymentId());

        payment.setRazorpaySignature(
                request.getRazorpaySignature());

        payment.setUpdatedAt(
                LocalDateTime.now());

        repository.save(payment);
      try {


          appointmentClient.confirmAppointment(
                  payment.getAppointmentId());
      }
      catch (FeignException ex){
          throw new PaymentException("There is something issue in appointment service "+ex.getMessage());
      }

        return PaymentMapper.toDto(payment);
    }

}