package com.hca.slot_service.utility;

import com.hca.slot_service.dto.SlotResponse;
import com.hca.slot_service.entity.DoctorSlot;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
    public SlotResponse map(DoctorSlot slot) {

        return SlotResponse.builder()
                .id(slot.getId())
                .doctorId(slot.getDoctorId())
                .slotDate(slot.getSlotDate())
                .startTime(slot.getStartTime())
                .endTime(slot.getEndTime())
                .status(slot.getStatus())
                .build();
    }
}
