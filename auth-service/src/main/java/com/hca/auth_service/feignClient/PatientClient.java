package com.hca.auth_service.feignClient;

import com.hca.auth_service.dto.UserRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PATIENT-SERVICE")
public interface PatientClient {

    @PostMapping("/api/patients")
    ResponseEntity<Object> createPatient(
            @RequestBody UserRequest request
    );
}