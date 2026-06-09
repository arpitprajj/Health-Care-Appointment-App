package com.hca.patient_service.entity;

import com.hca.patient_service.utility.BloodGroup;
import com.hca.patient_service.utility.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "patients")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID patientId;

    @Column(nullable = false, unique = true)
    private String userId;

    private String fullName;

    private String email;

    private String phoneNumber;

    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private BloodGroup bloodGroup;

    private String address;

    private String emergencyContactName;

    private String emergencyContactNumber;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}