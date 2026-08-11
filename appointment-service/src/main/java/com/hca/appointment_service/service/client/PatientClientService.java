package com.hca.appointment_service.service.client;

import com.hca.appointment_service.dto.PatientResponse;
import com.hca.appointment_service.exceptions.PatientServiceUnavailableException;
import com.hca.appointment_service.feign.PatientClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
@RequiredArgsConstructor
@Slf4j
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
        log.error(
                "CIRCUIT BREAKER FALLBACK - Patient Service unavailable. cause={}",
                throwable.toString());

        throw new PatientServiceUnavailableException(
                "Patient service is currently unavailable. "
                        + "Please try again later.");
    }


    private PatientResponse getPatientByIdFallback(
            UUID patientId,
            Throwable throwable) {
        log.error(
                "CIRCUIT BREAKER FALLBACK - Pateint Service unavailable. cause={}",
                throwable.toString());

        throw new PatientServiceUnavailableException(
                "Patient service is currently unavailable. "
                        + "Please try again later.");
    }
}