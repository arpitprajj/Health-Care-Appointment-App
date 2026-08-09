package com.hca.appointment_service.service.client;

import com.hca.appointment_service.dto.PatientResponse;
import com.hca.appointment_service.exceptions.PatientServiceUnavailableException;
import com.hca.appointment_service.feign.PatientClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
@RequiredArgsConstructor
public class PatientClientService {

    private final PatientClient patientClient;

    @CircuitBreaker(
            name = "patientService",
            fallbackMethod = "getPatientByUserIdFallback")
    public PatientResponse getPatientByUserId(
            String userId) {

        return patientClient.getPatientByUserId(userId);
    }

    @CircuitBreaker(
            name = "patientService",
            fallbackMethod = "getPatientByIdFallback")
    public PatientResponse getPatientById(
            UUID patientId) {

        return patientClient.getPatientById(patientId);
    }


    private PatientResponse getPatientByUserIdFallback(
            String userId,
            Throwable throwable) {

        throw new PatientServiceUnavailableException(
                "Patient service is currently unavailable. "
                        + "Please try again later.");
    }


    private PatientResponse getPatientByIdFallback(
            UUID patientId,
            Throwable throwable) {

        throw new PatientServiceUnavailableException(
                "Patient service is currently unavailable. "
                        + "Please try again later.");
    }
}