package com.hca.slot_service.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class GenerateSlotsRequest {

    private String doctorId;

    private LocalDate startDate;

    private LocalDate endDate;
}
