package com.hca.doctor_service.dto;

import lombok.*;

@Data
@Builder
public class DoctorResponse {

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

}