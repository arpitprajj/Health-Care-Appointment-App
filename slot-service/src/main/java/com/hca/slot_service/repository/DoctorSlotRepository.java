package com.hca.slot_service.repository;

import com.hca.slot_service.entity.DoctorSlot;
import com.hca.slot_service.utility.SlotStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface DoctorSlotRepository
        extends JpaRepository<DoctorSlot, UUID> {

    List<DoctorSlot> findByDoctorIdAndSlotDate(
            String doctorId,
            LocalDate slotDate);

    List<DoctorSlot> findByDoctorIdAndStatus(
            String doctorId,
            SlotStatus status);

    List<DoctorSlot> findByStatusAndReservedUntilBefore(
            SlotStatus status,
            LocalDateTime time);
}
