package com.hca.appointment_service.feign;

import com.hca.appointment_service.dto.PatientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PATIENT-SERVICE")
public interface PatientClient {

    @GetMapping("/api/patients/user/{userId}")
    PatientResponse getPatientByUserId(
            @PathVariable String userId);

}