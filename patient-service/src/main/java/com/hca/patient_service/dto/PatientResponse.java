package com.hca.patient_service.dto;
import com.hca.patient_service.utility.BloodGroup;
import com.hca.patient_service.utility.Gender;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;
@Data
@Builder
public class PatientResponse {

    private UUID patientId;

    private String userId;

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