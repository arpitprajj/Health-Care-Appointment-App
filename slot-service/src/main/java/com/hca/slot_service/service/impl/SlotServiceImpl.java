package com.hca.slot_service.service.impl;

import com.hca.slot_service.dto.GenerateSlotsRequest;
import com.hca.slot_service.dto.SlotResponse;
import com.hca.slot_service.entity.DoctorSlot;
import com.hca.slot_service.repository.DoctorSlotRepository;
import com.hca.slot_service.service.SlotService;
import com.hca.slot_service.utility.SlotStatus;
import com.hca.slot_service.utility.SlotTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public class SlotServiceImpl implements SlotService {
    @Autowired
    private DoctorSlotRepository repository;
    @Override
    public void generateSlots(
            GenerateSlotsRequest request) {

        LocalDate date = request.getStartDate();

        while (!date.isAfter(request.getEndDate())) {

            for (LocalTime[] slot : SlotTemplate.DAILY_SLOTS) {

                DoctorSlot doctorSlot =
                        DoctorSlot.builder()
                                .doctorId(request.getDoctorId())
                                .slotDate(date)
                                .startTime(slot[0])
                                .endTime(slot[1])
                                .status(SlotStatus.AVAILABLE)
                                .createdAt(LocalDateTime.now())
                                .updatedAt(LocalDateTime.now())
                                .build();

                repository.save(doctorSlot);
            }

            date = date.plusDays(1);
        }
    }

    @Override
    public List<SlotResponse> getAvailableSlots(String doctorId, LocalDate date) {
        return List.of();
    }

    @Override
    public SlotResponse reserveSlot(UUID slotId, String patientId) {
        return null;
    }

    @Override
    public SlotResponse bookSlot(UUID slotId) {
        return null;
    }

    @Override
    public void blockSlot(UUID slotId) {

    }

    @Override
    public void releaseExpiredSlots() {

    }
}
