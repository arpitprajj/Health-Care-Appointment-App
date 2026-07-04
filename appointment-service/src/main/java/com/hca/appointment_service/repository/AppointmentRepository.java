package com.hca.appointment_service.repository;

import com.hca.appointment_service.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AppointmentRepository
        extends JpaRepository<Appointment, UUID> {

    List<Appointment> findByPatientId(
            String patientId);

    List<Appointment> findByDoctorId(
            String doctorId);

}