package com.hca.patient_service.dto;
import com.hca.patient_service.utility.BloodGroup;
import com.hca.patient_service.utility.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdatePatientRequest {

    private String fullName;

    private String email;

    private String phoneNumber;

    private LocalDate dateOfBirth;

    private Gender gender;

    private BloodGroup bloodGroup;

    private String address;

    private String emergencyContactName;

    private String emergencyContactNumber;
}