package com.hca.doctor_service.entity;

import com.hca.doctor_service.dto.Specialization;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "doctors")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {

    @Id
    private String id;

    private String doctorId;

    private String fullName;

    private String email;

    private String phoneNumber;

    private Specialization specialization;

    private String qualification;

    private Integer experienceYears;

    private String hospitalName;

    private String city;

    private Double consultationFee;

    private Double averageRating;

    private Integer totalReviews;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}