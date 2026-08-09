package com.hca.auth_service.service.client;

import com.hca.auth_service.dto.UserRequest;
import com.hca.auth_service.exception.PatientServiceUnavailableException;
import com.hca.auth_service.feignClient.PatientClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientClientService {

    private final PatientClient patientClient;

    @CircuitBreaker(
            name = "patientService",
            fallbackMethod = "createPatientFallback")
    public ResponseEntity<Object> createPatient(
            UserRequest request) {

        return patientClient.createPatient(request);
    }

    private ResponseEntity<Object> createPatientFallback(
            UserRequest request,
            Throwable throwable) {

        throw new PatientServiceUnavailableException(
                "Patient service is currently unavailable. "
                        + "Registration cannot be completed. "
                        + "Please try again later.");
    }
}