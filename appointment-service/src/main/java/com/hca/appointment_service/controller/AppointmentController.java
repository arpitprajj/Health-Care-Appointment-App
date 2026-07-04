package com.hca.appointment_service.controller;

import com.hca.appointment_service.dto.AppointmentResponse;
import com.hca.appointment_service.dto.CreateAppointmentRequest;
import com.hca.appointment_service.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping("slots/{slotId}")
    public ResponseEntity<AppointmentResponse> createAppointment(

            @RequestHeader("X-User-Id")
            String userId,

            @RequestHeader("X-Role")
            String role,

            @PathVariable String slotId) {

        return ResponseEntity.ok(

                appointmentService.createAppointment(

                        userId,

                        role,

                        slotId));
    }
    @GetMapping("/{appointmentId}")
    public ResponseEntity<AppointmentResponse> getAppointment(
            @PathVariable UUID appointmentId) {

        return ResponseEntity.ok(
                appointmentService.getAppointment(
                        appointmentId));
    }

    /**
     * Patient appointment history.
     */
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<AppointmentResponse>>
    getPatientAppointments(
            @PathVariable String patientId) {

        return ResponseEntity.ok(
                appointmentService.getPatientAppointments(
                        patientId));
    }

    /**
     * Doctor appointment history.
     */
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<AppointmentResponse>>
    getDoctorAppointments(
            @PathVariable String doctorId) {

        return ResponseEntity.ok(
                appointmentService.getDoctorAppointments(
                        doctorId));
    }

    /**
     * Patient cancels appointment.
     */
    @PatchMapping("/{appointmentId}/cancel")
    public ResponseEntity<AppointmentResponse> cancelAppointment(
            @PathVariable UUID appointmentId) {

        return ResponseEntity.ok(
                appointmentService.cancelAppointment(
                        appointmentId));
    }



}

