package com.hca.slot_service.entity;

import com.hca.slot_service.utility.SlotStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(
        name = "doctor_slots",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "doctorId",
                                "slotDate",
                                "startTime"
                        }
                )
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String doctorId;

    private LocalDate slotDate;

    private LocalTime startTime;

    private LocalTime endTime;

    @Enumerated(EnumType.STRING)
    private SlotStatus status;

    private String reservedByPatientId;

    private LocalDateTime reservedUntil;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}