package com.hca.doctor_service.dto;

import com.hca.doctor_service.entity.Doctor;

public class RequestResponseMapper {
    public static DoctorResponse mapToResponse(
            Doctor doctor) {

        return DoctorResponse.builder()
                .doctorId(doctor.getDoctorId())
                .fullName(doctor.getFullName())
                .email(doctor.getEmail())
                .phoneNumber(doctor.getPhoneNumber())
                .specialization(
                        doctor.getSpecialization())
                .qualification(
                        doctor.getQualification())
                .experienceYears(
                        doctor.getExperienceYears())
                .hospitalName(
                        doctor.getHospitalName())
                .city(doctor.getCity())
                .consultationFee(
                        doctor.getConsultationFee())
                .averageRating(
                        doctor.getAverageRating())
                .totalReviews(
                        doctor.getTotalReviews())
                .build();
    }
}
