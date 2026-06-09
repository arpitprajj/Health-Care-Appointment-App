package com.hca.patient_service.service.impl;



import com.hca.patient_service.dto.CreatePatientRequest;
import com.hca.patient_service.dto.PatientResponse;
import com.hca.patient_service.dto.UpdatePatientRequest;
import com.hca.patient_service.entity.Patient;
import com.hca.patient_service.exception.PatientNotFoundException;
import com.hca.patient_service.repository.PatientRepository;
import com.hca.patient_service.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    @Autowired
    private final PatientRepository patientRepository;


    @Transactional
    public PatientResponse createPatient(
            CreatePatientRequest request) {

        if (patientRepository.existsByUserId(
                request.getUserId())) {

            throw new RuntimeException(
                    "Patient already exists for user");
        }

        Patient patient = Patient.builder()
                .userId(request.getUserId())
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .dateOfBirth(request.getDateOfBirth())
                .gender(request.getGender())
                .bloodGroup(request.getBloodGroup())
                .address(request.getAddress())
                .emergencyContactName(
                        request.getEmergencyContactName())
                .emergencyContactNumber(
                        request.getEmergencyContactNumber())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Patient savedPatient =
                patientRepository.save(patient);

        return map(savedPatient);
    }


    public PatientResponse getPatientById(
            UUID patientId) {

        Patient patient =
                patientRepository.findById(patientId)
                        .orElseThrow(() ->
                                new PatientNotFoundException(
                                        "Patient not found"));

        return map(patient);
    }

    @Override
    public PatientResponse getPatientByUserId(
            String userId) {

        Patient patient =
                patientRepository.findByUserId(userId)
                        .orElseThrow(() ->
                                new PatientNotFoundException(
                                        "Patient not found"));

        return map(patient);
    }


    @Override
    @Transactional
    public PatientResponse updatePatient(
            UUID patientId,
            UpdatePatientRequest request) {

        Patient patient =
                patientRepository.findById(patientId)
                        .orElseThrow(() ->
                                new PatientNotFoundException(
                                        "Patient not found"));

        patient.setFullName(request.getFullName());
        patient.setEmail(request.getEmail());
        patient.setPhoneNumber(
                request.getPhoneNumber());
        patient.setDateOfBirth(
                request.getDateOfBirth());
        patient.setGender(request.getGender());
        patient.setBloodGroup(
                request.getBloodGroup());
        patient.setAddress(request.getAddress());
        patient.setEmergencyContactName(
                request.getEmergencyContactName());
        patient.setEmergencyContactNumber(
                request.getEmergencyContactNumber());

        patient.setUpdatedAt(
                LocalDateTime.now());

        Patient updatedPatient =
                patientRepository.save(patient);

        return map(updatedPatient);
    }

    @Override
    @Transactional
    public void deletePatient(
            UUID patientId) {

        Patient patient =
                patientRepository.findById(patientId)
                        .orElseThrow(() ->
                                new PatientNotFoundException(
                                        "Patient not found"));

        patientRepository.delete(patient);
    }

    private PatientResponse map(
            Patient patient) {

        return PatientResponse.builder()
                .patientId(patient.getPatientId())
                .userId(patient.getUserId())
                .fullName(patient.getFullName())
                .email(patient.getEmail())
                .phoneNumber(patient.getPhoneNumber())
                .dateOfBirth(patient.getDateOfBirth())
                .gender(patient.getGender())
                .bloodGroup(patient.getBloodGroup())
                .address(patient.getAddress())
                .emergencyContactName(
                        patient.getEmergencyContactName())
                .emergencyContactNumber(
                        patient.getEmergencyContactNumber())
                .build();
    }
}