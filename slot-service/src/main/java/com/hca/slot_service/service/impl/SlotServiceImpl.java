package com.hca.slot_service.service.impl;

import com.hca.slot_service.dto.GenerateSlotsRequest;
import com.hca.slot_service.dto.SlotResponse;
import com.hca.slot_service.entity.DoctorSlot;
import com.hca.slot_service.exception.SlotNotFoundException;
import com.hca.slot_service.repository.DoctorSlotRepository;
import com.hca.slot_service.service.SlotService;
import com.hca.slot_service.utility.Mapper;
import com.hca.slot_service.utility.SlotStatus;
import com.hca.slot_service.utility.SlotTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
public class SlotServiceImpl implements SlotService {
    @Autowired
    private DoctorSlotRepository repository;
    @Autowired
    private Mapper mapper;
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
    public SlotResponse getSlot(UUID slotId) {
        DoctorSlot slot=repository.findById(slotId).orElseThrow(()->new SlotNotFoundException("NOT SUCH SLOT FOUND "+slotId));
        return mapper.map(slot);
    }

    @Override
    public List<SlotResponse> getAvailableSlots(String doctorId, LocalDate date) {
        return List.of();
    }

    @Override
    @Transactional
    public SlotResponse reserveSlot(
            UUID slotId,
            String patientId,UUID appointmentId) {

        DoctorSlot slot =
                repository.findByIdForUpdate(slotId)
                        .orElseThrow(
                                () -> new SlotNotFoundException(
                                        "Slot not found"));

        if (slot.getStatus() != SlotStatus.AVAILABLE) {
            throw new RuntimeException(
                    "Slot not available");
        }

        slot.setStatus(SlotStatus.RESERVED);
        slot.setReservedByPatientId(patientId);
        slot.setReservedUntil(
                LocalDateTime.now().plusMinutes(5));
        slot.setReservedForAppointmentId(appointmentId);
        repository.save(slot);

        return mapper.map(slot);
    }

    @Override
    @Transactional
    public SlotResponse bookSlot(UUID slotId,UUID appointmentId) {

        DoctorSlot slot =
                repository.findByIdForUpdate(slotId)
                        .orElseThrow(
                                () -> new SlotNotFoundException(
                                        "Slot not found"));
        if (!appointmentId.equals(slot.getReservedForAppointmentId())) {

            throw new RuntimeException(
                    "Reservation does not belong to this appointment");
        }

        if (slot.getStatus() != SlotStatus.RESERVED) {
            throw new RuntimeException(
                    "Slot not reserved");
        }

        slot.setStatus(SlotStatus.BOOKED);

        repository.save(slot);

        return mapper.map(slot);
    }

    @Override
    @Transactional
    public SlotResponse releaseSlot(UUID slotId,UUID appointmentId) {
        DoctorSlot slot=repository.findByIdForUpdate(slotId).orElseThrow(()->new SlotNotFoundException("slot not found"));
        if (!appointmentId.equals(slot.getReservedForAppointmentId())) {

            throw new RuntimeException(
                    "Reservation does not belong to this appointment");
        }
        slot.setStatus(SlotStatus.AVAILABLE);

        slot.setReservedByPatientId(null);

        slot.setReservedUntil(null);
        slot.setReservedForAppointmentId(null);
        repository.save(slot);
        return mapper.map(slot);
    }

    @Override
    public List<DoctorSlot> findByDoctorIdAndSlotDate(String doctorId, LocalDate slotDate) {
        return repository.findByDoctorIdAndSlotDate(doctorId, slotDate);
    }

    @Override
    public List<DoctorSlot> findByDoctorIdAndStatus(String doctorId, SlotStatus status) {
        return repository.findByDoctorIdAndStatus(doctorId, status);
    }


    @Override
    @Transactional
    public void releaseExpiredSlots() {

        List<DoctorSlot> expiredSlots =
                repository.findByStatusAndReservedUntilBefore(
                        SlotStatus.RESERVED,
                        LocalDateTime.now());

        expiredSlots.forEach(slot -> {

            slot.setStatus(SlotStatus.AVAILABLE);
            slot.setReservedByPatientId(null);
            slot.setReservedUntil(null);

        });

        repository.saveAll(expiredSlots);
    }
}
