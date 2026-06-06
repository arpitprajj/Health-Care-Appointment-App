package com.hca.doctor_service.dto;

import lombok.Data;

@Data
public class DoctorRequest {

    private String fullName;

    private String email;

    private String phoneNumber;

    private Specialization specialization;

    private String qualification;

    private Integer experienceYears;

    private String hospitalName;

    private String city;

    private Double consultationFee;
}
