package com.hca.slot_service.dto;

import com.hca.slot_service.utility.SlotStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@Builder
public class SlotResponse {

    private UUID id;

    private String doctorId;

    private LocalDate slotDate;

    private LocalTime startTime;

    private LocalTime endTime;

    private SlotStatus status;
}