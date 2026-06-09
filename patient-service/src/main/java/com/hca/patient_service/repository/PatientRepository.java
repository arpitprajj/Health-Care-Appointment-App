package com.hca.patient_service.repository;

import com.hca.patient_service.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientRepository
        extends JpaRepository<Patient, UUID> {

    Optional<Patient> findByUserId(
            String userId);

    Optional<Patient> findByEmail(
            String email);

    boolean existsByUserId(
            String userId);
}