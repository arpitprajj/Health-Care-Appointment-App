package com.hca.doctor_service.service;

import com.hca.doctor_service.dto.DoctorRequest;
import com.hca.doctor_service.dto.DoctorResponse;
import com.hca.doctor_service.dto.Specialization;
import java.util.List;
public interface DoctorService {

    DoctorResponse createDoctor(
            DoctorRequest request);

    DoctorResponse getDoctorById(
            String doctorId);

    List<DoctorResponse> getAllDoctors();

    DoctorResponse updateDoctor(
            String doctorId,
            DoctorRequest request);

    void deleteDoctor(
            String doctorId);

    List<DoctorResponse> searchDoctors(
            Specialization specialization,
            String city);

}
