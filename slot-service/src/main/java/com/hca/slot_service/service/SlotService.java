package com.hca.slot_service.service;

import com.hca.slot_service.dto.GenerateSlotsRequest;
import com.hca.slot_service.dto.SlotResponse;
import com.hca.slot_service.entity.DoctorSlot;
import com.hca.slot_service.utility.SlotStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
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
    List<DoctorSlot> findByDoctorIdAndSlotDate(
            String doctorId,
            LocalDate slotDate);
    List<DoctorSlot> findByDoctorIdAndStatus(
            String doctorId,
            SlotStatus status);

    void releaseExpiredSlots();
}