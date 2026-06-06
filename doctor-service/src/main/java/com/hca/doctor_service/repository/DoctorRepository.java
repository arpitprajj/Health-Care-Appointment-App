package com.hca.doctor_service.repository;

import com.hca.doctor_service.dto.Specialization;
import com.hca.doctor_service.entity.Doctor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository
        extends MongoRepository<Doctor,String> {

    Optional<Doctor> findByEmail(String email);

    List<Doctor> findBySpecialization(
            Specialization specialization);

    List<Doctor> findByCity(String city);

    List<Doctor> findBySpecializationAndCity(
            Specialization specialization,
            String city);

}