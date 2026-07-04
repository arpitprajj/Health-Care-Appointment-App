package com.hca.appointment_service.dto;

import com.hca.appointment_service.enums.SlotStatus;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
public class SlotResponse {
    private UUID id;

    private String doctorId;

    private SlotStatus status;

    private LocalDate slotDate;

    private LocalTime startTime;

    private LocalTime endTime;
}
