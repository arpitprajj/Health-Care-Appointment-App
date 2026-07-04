package com.hca.appointment_service.dto;

import com.hca.appointment_service.entity.Appointment;

public class AppointmentMapper {

    public static AppointmentResponse toDto(Appointment appointment) {
        if (appointment == null) {
            return null;
        }

        AppointmentResponse dto = new AppointmentResponse();
        dto.setAppointmentId(appointment.getAppointmentId());
        dto.setPatientId(appointment.getPatientId());
        dto.setDoctorId(appointment.getDoctorId());
        dto.setSlotId(appointment.getSlotId());
        dto.setAppointmentStatus(appointment.getAppointmentStatus());
        dto.setPaymentStatus(appointment.getPaymentStatus());
        dto.setMeetingLink(appointment.getMeetingLink());
        dto.setNotes(appointment.getNotes());
        dto.setAppointmentTime(appointment.getAppointmentTime());

        return dto;
    }
}
