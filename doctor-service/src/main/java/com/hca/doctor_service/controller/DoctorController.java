package com.hca.doctor_service.controller;

import com.hca.doctor_service.dto.DoctorRequest;
import com.hca.doctor_service.dto.DoctorResponse;
import com.hca.doctor_service.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {

    @Autowired
    private final DoctorService doctorService;

    @PostMapping
    public ResponseEntity<DoctorResponse>
    createDoctor(
            @RequestBody DoctorRequest request) {

        return ResponseEntity.ok(
                doctorService.createDoctor(request));
    }

    @GetMapping("/{doctorId}")
    public ResponseEntity<DoctorResponse>
    getDoctor(
            @PathVariable String doctorId) {

        return ResponseEntity.ok(
                doctorService.getDoctorById(
                        doctorId));
    }

    @GetMapping
    public ResponseEntity<List<DoctorResponse>>
    getAllDoctors() {

        return ResponseEntity.ok(
                doctorService.getAllDoctors());
    }

    @PutMapping("/{doctorId}")
    public ResponseEntity<DoctorResponse>
    updateDoctor(
            @PathVariable String doctorId,
            @RequestBody DoctorRequest request) {

        return ResponseEntity.ok(
                doctorService.updateDoctor(
                        doctorId,
                        request));
    }

    @DeleteMapping("/{doctorId}")
    public ResponseEntity<Void>
    deleteDoctor(
            @PathVariable String doctorId) {

        doctorService.deleteDoctor(
                doctorId);

        return ResponseEntity.noContent()
                .build();
    }

}