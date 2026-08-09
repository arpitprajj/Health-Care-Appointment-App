package com.hca.payment_service.service;

import com.hca.payment_service.dto.AppointmentResponse;
import com.hca.payment_service.exception.AppointmentServiceUnavailableException;
import com.hca.payment_service.feign.AppointmentClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppointmentClientService {

    private final AppointmentClient appointmentClient;

    @CircuitBreaker(
            name = "appointmentService",
            fallbackMethod = "confirmAppointmentFallback")
    public AppointmentResponse confirmAppointment(
            UUID appointmentId) {

        return appointmentClient.confirmAppointment(
                appointmentId);
    }

    @CircuitBreaker(
            name = "appointmentService",
            fallbackMethod = "getAppointmentFallback")
    public AppointmentResponse getAppointment(
            UUID appointmentId) {

        return appointmentClient.getAppointment(
                appointmentId);
    }


    private AppointmentResponse confirmAppointmentFallback(
            UUID appointmentId,
            Throwable throwable) {

        throw new AppointmentServiceUnavailableException(
                "Appointment service is currently unavailable. "
                        + "Payment confirmation cannot be completed. "
                        + "Please try again later.");
    }


    private AppointmentResponse getAppointmentFallback(
            UUID appointmentId,
            Throwable throwable) {

        throw new AppointmentServiceUnavailableException(
                "Appointment service is currently unavailable. "
                        + "Please try again later.");
    }
}