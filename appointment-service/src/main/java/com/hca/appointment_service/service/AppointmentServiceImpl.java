package com.hca.appointment_service.service;

import com.hca.appointment_service.dto.*;
import com.hca.appointment_service.entity.Appointment;
import com.hca.appointment_service.enums.*;
import com.hca.appointment_service.events.AppointmentConfirmedEvent;
import com.hca.appointment_service.events.AppointmentReservedEvent;
import com.hca.appointment_service.exceptions.AppointmentException;
import com.hca.appointment_service.exceptions.AppointmentNotFoundException;
import com.hca.appointment_service.feign.DoctorClient;
import com.hca.appointment_service.feign.PatientClient;
import com.hca.appointment_service.feign.SlotClient;
import com.hca.appointment_service.producer.AppointmentEventProducer;
import com.hca.appointment_service.repository.AppointmentRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppointmentServiceImpl implements AppointmentService{
    private final PatientClient patientClient;
    private final SlotClient slotClient;
    private final DoctorClient doctorClient;
    private final AppointmentRepository repository;
    private final AppointmentEventProducer producer;


    @Override
    @Transactional
    public AppointmentResponse createAppointment(
            String userId,
            String role,
            String slotId) {

        if (!Role.PATIENT.name().equals(role)) {
            throw new AppointmentException(
                    "Only patients can book appointments");
        }

        PatientResponse patient =
                patientClient.getPatientByUserId(userId);

        SlotResponse slot =
                slotClient.getSlot(
                        UUID.fromString(slotId));


        if (slot.getStatus() != SlotStatus.AVAILABLE) {
            throw new RuntimeException(
                    "Selected slot is not available");
        }

        Appointment appointment =
                Appointment.builder()
                        .patientId(
                                patient.getPatientId().toString())
                        .doctorId(slot.getDoctorId())
                        .slotId(slot.getId())
                        .consultationFee(BigDecimal.valueOf(500.0))
                        .appointmentStatus(
                                AppointmentStatus.PENDING_PAYMENT)
                        .paymentStatus(
                                PaymentStatus.PENDING)
                        .appointmentTime(
                                LocalDateTime.of(
                                        slot.getSlotDate(),
                                        slot.getStartTime()))
                        .meetingLink(null)
                        .notes(null)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build();

        appointment = repository.save(appointment);

        try {

            slotClient.reserveSlot(
                    appointment.getSlotId(),
                    appointment.getPatientId(),
                    appointment.getAppointmentId());

        } catch (FeignException ex) {

            appointment.setAppointmentStatus(
                    AppointmentStatus.CANCELLED);

            appointment.setPaymentStatus(
                    PaymentStatus.FAILED);

            appointment.setUpdatedAt(
                    LocalDateTime.now());

            repository.save(appointment);

            throw new AppointmentException(
                    "Unable to reserve slot" +
                    ex.getMessage());
        }
        producer.publishReserved(

                AppointmentReservedEvent.builder()

                        .appointmentId(
                                appointment.getAppointmentId())

                        .patientId(
                                appointment.getPatientId())

                        .patientEmail(
                                patient.getEmail())

                        .patientName(
                                patient.getFullName())

                        .doctorId(
                                appointment.getDoctorId())

                        .consultationFee(
                                appointment.getConsultationFee())

                        .appointmentTime(
                                appointment.getAppointmentTime())

                        .eventType(
                                EventType.APPOINTMENT_RESERVED)

                        .build()

        );
        log.info("======================================Appointment Reserved"+appointment.getAppointmentId());

        return AppointmentMapper.toDto(appointment);
    }
    @Override
    @Transactional
    public AppointmentResponse confirmAppointment(UUID appointmentId) {

        Appointment appointment = repository.findById(appointmentId)
                .orElseThrow(() ->
                        new AppointmentNotFoundException(
                                "Appointment not found "+appointmentId));

        if (appointment.getAppointmentStatus() == AppointmentStatus.CONFIRMED) {
            return AppointmentMapper.toDto(appointment);
        }
        try {
            slotClient.bookSlot(
                    appointment.getSlotId(),
                    appointment.getAppointmentId());

            appointment.setAppointmentStatus(
                    AppointmentStatus.CONFIRMED);

            appointment.setPaymentStatus(
                    PaymentStatus.SUCCESS);

            appointment.setUpdatedAt(LocalDateTime.now());

            // TODO
            // Generate Google Meet/Zoom link here

            appointment.setMeetingLink(
                    "https://meet.google.com/abc-defg-hij");

            appointment.setNotes(
                    "Appointment confirmed successfully.");

            repository.save(appointment);
            try {
                DoctorResponse doctor = doctorClient.getDoctor(appointment.getDoctorId());
                PatientResponse patient = patientClient.getPatientById(UUID.fromString(appointment.getPatientId()));
                producer.publishConfirmed(

                        AppointmentConfirmedEvent.builder()

                                .appointmentId(
                                        appointment.getAppointmentId())

                                .patientId(
                                        appointment.getPatientId())

                                .patientEmail(
                                        patient.getEmail())

                                .patientName(
                                        patient.getFullName())

                                .doctorId(
                                        appointment.getDoctorId())

                                .doctorEmail(
                                        doctor.getEmail())

                                .doctorName(
                                        doctor.getFullName())

                                .appointmentTime(
                                        appointment.getAppointmentTime())

                                .eventType(
                                        EventType.APPOINTMENT_CONFIRMED)

                                .build()

                );
                log.info("====================================Appointment Confirmed "+appointment.getAppointmentId());
            } catch (Exception e) {
                throw new AppointmentException(e.getMessage());
            }
            return AppointmentMapper.toDto(appointment);
        }
        catch (FeignException ex) {

            // Compensation

            appointment.setAppointmentStatus(
                    AppointmentStatus.CANCELLED);

            appointment.setPaymentStatus(
                    PaymentStatus.FAILED);

            repository.save(appointment);

            throw new AppointmentException(
                    "Unable to book slot after payment "+ex.getMessage());
        }

    }

    @Override
    @Transactional
    public AppointmentResponse cancelAppointment(UUID appointmentId) {

        Appointment appointment = repository.findById(appointmentId)
                .orElseThrow(() ->
                        new AppointmentNotFoundException(
                                "Appointment not found "+appointmentId));

        if (appointment.getAppointmentStatus() == AppointmentStatus.CANCELLED) {
            return AppointmentMapper.toDto(appointment);
        }

        if (appointment.getAppointmentStatus() == AppointmentStatus.CONFIRMED) {

            // Later:
            // initiate refund

        } else {

            slotClient.releaseSlot(
                    appointment.getSlotId(),
                    appointment.getAppointmentId());
        }

        appointment.setAppointmentStatus(
                AppointmentStatus.CANCELLED);

        appointment.setPaymentStatus(
                PaymentStatus.FAILED);

        appointment.setUpdatedAt(LocalDateTime.now());

        repository.save(appointment);

        return AppointmentMapper.toDto(appointment);
    }

    @Override
    public AppointmentResponse getAppointment(UUID appointmentId) {

        Appointment appointment = repository.findById(appointmentId)
                .orElseThrow(() ->
                        new AppointmentNotFoundException(
                                "Appointment not found"));

        return AppointmentMapper.toDto(appointment);
    }

    @Override
    public List<AppointmentResponse> getPatientAppointments(String patientId) {
        List<Appointment> appointments=repository.findByPatientId(patientId);
        return appointments.stream().map((appointment)->AppointmentMapper.toDto(appointment)).collect(Collectors.toList());
    }

    @Override
    public List<AppointmentResponse> getDoctorAppointments(String doctorId) {
        List<Appointment>appointments=repository.findByPatientId(doctorId);
        return appointments.stream().map((appointment)->AppointmentMapper.toDto(appointment)).collect(Collectors.toList());
    }
}
