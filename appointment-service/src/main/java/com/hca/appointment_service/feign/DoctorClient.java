package com.hca.appointment_service.feign;

import com.hca.appointment_service.dto.DoctorResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "DOCTOR-SERVICE")
public interface DoctorClient {

    @GetMapping("/api/doctors/{doctorId}")
    DoctorResponse getDoctor(
            @PathVariable String doctorId);

}