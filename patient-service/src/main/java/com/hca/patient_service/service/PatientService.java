package com.hca.patient_service.service;

import com.hca.patient_service.dto.CreatePatientRequest;
import com.hca.patient_service.dto.PatientResponse;
import com.hca.patient_service.dto.UpdatePatientRequest;

import java.util.UUID;

public interface PatientService {

    PatientResponse createPatient(
            CreatePatientRequest request);

    PatientResponse getPatientById(
            UUID patientId);

    PatientResponse getPatientByUserId(
            String userId);

    PatientResponse updatePatient(
            UUID patientId,
            UpdatePatientRequest request);


    void deletePatient(
            UUID patientId);
}