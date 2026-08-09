package com.hca.appointment_service.service.client;

import com.hca.appointment_service.dto.DoctorResponse;
import com.hca.appointment_service.exceptions.DoctorServiceUnavailableException;
import com.hca.appointment_service.feign.DoctorClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorClientService {

    private final DoctorClient doctorClient;

    @CircuitBreaker(
            name = "doctorService",
            fallbackMethod = "getDoctorFallback")
    public DoctorResponse getDoctor(String doctorId) {

        return doctorClient.getDoctor(doctorId);
    }

    private DoctorResponse getDoctorFallback(
            String doctorId,
            Throwable throwable) {

        throw new DoctorServiceUnavailableException(
                "Doctor service is currently unavailable. "
                        + "Please try again later.");
    }
}