package com.hca.slot_service.repository;

import com.hca.slot_service.entity.DoctorSlot;
import com.hca.slot_service.utility.SlotStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
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
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
            SELECT s
                FROM DoctorSlot s
                    WHERE s.id = :slotId
                                        """)
    Optional<DoctorSlot> findByIdForUpdate(UUID slotId);
}
