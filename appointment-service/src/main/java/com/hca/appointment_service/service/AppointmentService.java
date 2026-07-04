package com.hca.appointment_service.service;

import com.hca.appointment_service.dto.AppointmentResponse;
import com.hca.appointment_service.dto.CreateAppointmentRequest;

import java.util.List;
import java.util.UUID;

public interface AppointmentService {

    AppointmentResponse createAppointment(
            String userId,
            String role,
            String slotId);

    AppointmentResponse confirmAppointment(
            UUID appointmentId);

    AppointmentResponse cancelAppointment(
            UUID appointmentId);

    AppointmentResponse getAppointment(
            UUID appointmentId);

    List<AppointmentResponse> getPatientAppointments(
            String patientId);
    List<AppointmentResponse>getDoctorAppointments(String doctorId);

}