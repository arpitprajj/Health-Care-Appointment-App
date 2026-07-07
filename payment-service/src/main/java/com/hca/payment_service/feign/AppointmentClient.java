package com.hca.payment_service.feign;

import com.hca.payment_service.dto.AppointmentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "APPOINTMENT-SERVICE")
public interface AppointmentClient {

    @PatchMapping(
            "/internal/appointments/{appointmentId}/confirm")
    AppointmentResponse confirmAppointment(
            @PathVariable UUID appointmentId);

    @GetMapping("api/appointments/{appointmentId}")
    AppointmentResponse getAppointment(@PathVariable UUID appointmentId);

}