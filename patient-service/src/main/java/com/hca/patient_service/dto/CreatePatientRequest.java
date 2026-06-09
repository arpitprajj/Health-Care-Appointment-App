package com.hca.patient_service.dto;

import com.hca.patient_service.utility.BloodGroup;
import com.hca.patient_service.utility.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


import java.time.LocalDate;

@Data
public class CreatePatientRequest {

    @NotBlank
    private String userId;

    @NotBlank
    private String fullName;

    @Email
    private String email;

    @Pattern(regexp = "^[6-9]\\d{9}$",message = "Phone number must be 10 digits starting with 6-9")
    private String phoneNumber;

    private LocalDate dateOfBirth;

    private Gender gender;

    private BloodGroup bloodGroup;

    private String address;

    private String emergencyContactName;

    private String emergencyContactNumber;
}
