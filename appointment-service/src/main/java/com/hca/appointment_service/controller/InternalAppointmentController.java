package com.hca.appointment_service.controller;

import com.hca.appointment_service.dto.AppointmentResponse;
import com.hca.appointment_service.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/internal/appointments")
@RequiredArgsConstructor
public class InternalAppointmentController {

    private final AppointmentService appointmentService;

    /**
     * Called ONLY by Payment Service after successful payment.
     */
    @PatchMapping("/{appointmentId}/confirm")
    public ResponseEntity<AppointmentResponse> confirmAppointment(
            @PathVariable UUID appointmentId) {

        return ResponseEntity.ok(
                appointmentService.confirmAppointment(
                        appointmentId));
    }
}