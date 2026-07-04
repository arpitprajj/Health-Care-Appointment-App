package com.hca.appointment_service.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class PatientResponse {
    private UUID patientId;

    private String userId;

    private String fullName;

    private String email;

    private String phoneNumber;
}
