package com.hca.patient_service.controller;



import com.hca.patient_service.dto.CreatePatientRequest;
import com.hca.patient_service.dto.PatientResponse;
import com.hca.patient_service.dto.UpdatePatientRequest;
import com.hca.patient_service.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @PostMapping
    public ResponseEntity<PatientResponse> createPatient(
            @Valid @RequestBody CreatePatientRequest request) {

        PatientResponse response =
                patientService.createPatient(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{patientId}")
    public ResponseEntity<PatientResponse> getPatientById(
            @PathVariable UUID patientId) {

        return ResponseEntity.ok(
                patientService.getPatientById(patientId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<PatientResponse> getPatientByUserId(
            @PathVariable String userId) {

        return ResponseEntity.ok(
                patientService.getPatientByUserId(userId));
    }

    @PutMapping("/{patientId}")
    public ResponseEntity<PatientResponse> updatePatient(
            @PathVariable UUID patientId,
            @Valid @RequestBody UpdatePatientRequest request) {

        return ResponseEntity.ok(
                patientService.updatePatient(
                        patientId,
                        request));
    }

    @DeleteMapping("/{patientId}")
    public ResponseEntity<Void> deletePatient(
            @PathVariable UUID patientId) {

        patientService.deletePatient(patientId);

        return ResponseEntity.noContent().build();
    }
}