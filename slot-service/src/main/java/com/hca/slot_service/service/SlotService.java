package com.hca.slot_service.service;

import com.hca.slot_service.dto.GenerateSlotsRequest;
import com.hca.slot_service.dto.SlotResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface SlotService {

    void generateSlots(
            GenerateSlotsRequest request);

    List<SlotResponse> getAvailableSlots(
            String doctorId,
            LocalDate date);

    SlotResponse reserveSlot(
            UUID slotId,
            String patientId);

    SlotResponse bookSlot(
            UUID slotId);

    void blockSlot(
            UUID slotId);

    void releaseExpiredSlots();
}