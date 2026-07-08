package com.hca.doctor_service.service.impl;

import com.hca.doctor_service.Exception.DoctorNotFoundException;
import com.hca.doctor_service.dto.*;
import com.hca.doctor_service.entity.Doctor;
import com.hca.doctor_service.repository.DoctorRepository;
import com.hca.doctor_service.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private final DoctorRepository doctorRepository;

    @Override
    public DoctorResponse createDoctor(DoctorRequest request) {

        Doctor doctor = Doctor.builder()
                .doctorId(UUID.randomUUID().toString())
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .userId(request.getUserId())
                .specialization(request.getSpecialization())
                .qualification(request.getQualification())
                .experienceYears(request.getExperienceYears())
                .hospitalName(request.getHospitalName())
                .city(request.getCity())
                .consultationFee(request.getConsultationFee())
                .averageRating(0.0)
                .totalReviews(0)
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Doctor savedDoctor = doctorRepository.save(doctor);

        return mapToResponse(savedDoctor);
    }


    @Override
    public DoctorResponse getDoctorById(String doctorId) {

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() ->
                        new DoctorNotFoundException(
                                "Doctor not found with id : " + doctorId));

        return mapToResponse(doctor);
    }

    @Override
    public List<DoctorResponse> getAllDoctors() {

        return doctorRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public DoctorResponse updateDoctor(
            String doctorId,
            DoctorRequest request) {

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() ->
                        new DoctorNotFoundException(
                                "Doctor not found with id : " + doctorId));

        doctor.setFullName(request.getFullName());
        doctor.setPhoneNumber(request.getPhoneNumber());
        doctor.setSpecialization(request.getSpecialization());
        doctor.setQualification(request.getQualification());
        doctor.setExperienceYears(request.getExperienceYears());
        doctor.setHospitalName(request.getHospitalName());
        doctor.setCity(request.getCity());
        doctor.setConsultationFee(request.getConsultationFee());
        doctor.setUpdatedAt(LocalDateTime.now());

        Doctor updatedDoctor = doctorRepository.save(doctor);

        return mapToResponse(updatedDoctor);
    }

    @Override
    public void deleteDoctor(String doctorId) {

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() ->
                        new DoctorNotFoundException(
                                "Doctor not found with id : " + doctorId));

        doctorRepository.delete(doctor);
    }

    @Override
    public List<DoctorResponse> searchDoctors(
            Specialization specialization,
            String city) {

        List<Doctor> doctors;

        if (specialization != null && city != null) {
            doctors = doctorRepository
                    .findBySpecializationAndCity(
                            specialization,
                            city);

        } else if (specialization != null) {

            doctors = doctorRepository
                    .findBySpecialization(
                            specialization);

        } else if (city != null) {

            doctors = doctorRepository
                    .findByCity(city);

        } else {

            doctors = doctorRepository.findAll();
        }

        return doctors.stream()
                .map(this::mapToResponse)
                .toList();
    }

    private DoctorResponse mapToResponse(
            Doctor doctor) {

        return DoctorResponse.builder()
                .doctorId(doctor.getDoctorId())
                .fullName(doctor.getFullName())
                .email(doctor.getEmail())
                .phoneNumber(doctor.getPhoneNumber())
                .specialization(doctor.getSpecialization())
                .qualification(doctor.getQualification())
                .experienceYears(doctor.getExperienceYears())
                .hospitalName(doctor.getHospitalName())
                .city(doctor.getCity())
                .consultationFee(doctor.getConsultationFee())
                .averageRating(doctor.getAverageRating())
                .totalReviews(doctor.getTotalReviews())
                .build();
    }
}