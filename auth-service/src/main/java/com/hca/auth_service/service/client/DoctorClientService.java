package com.hca.auth_service.service.client;

import com.hca.auth_service.dto.UserRequest;
import com.hca.auth_service.exception.DoctorServiceUnavailableException;
import com.hca.auth_service.feignClient.DoctorClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorClientService {

    private final DoctorClient doctorClient;

    @CircuitBreaker(
            name = "doctorService",
            fallbackMethod = "createDoctorFallback")
    public ResponseEntity<Object> createDoctor(
            UserRequest request) {

        return doctorClient.createDoctor(request);
    }

    private ResponseEntity<Object> createDoctorFallback(
            UserRequest request,
            Throwable throwable) {

        throw new DoctorServiceUnavailableException(
                "Doctor service is currently unavailable. "
                        + "Registration cannot be completed. "
                        + "Please try again later.");
    }
}