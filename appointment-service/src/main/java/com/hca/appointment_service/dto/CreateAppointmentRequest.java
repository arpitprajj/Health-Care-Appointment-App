package com.hca.appointment_service.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateAppointmentRequest {

    private String doctorId;

    private UUID slotId;

}