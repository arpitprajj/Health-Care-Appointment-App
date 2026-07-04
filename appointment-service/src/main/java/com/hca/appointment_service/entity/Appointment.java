package com.hca.appointment_service.entity;

import com.hca.appointment_service.enums.AppointmentStatus;
import com.hca.appointment_service.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "appointments")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID appointmentId;

    private String patientId;

    private String doctorId;

    private UUID slotId;
    private BigDecimal consultationFee;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus appointmentStatus;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private String meetingLink;

    private String notes;

    private LocalDateTime appointmentTime;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}