package com.hca.slot_service.utility;

import com.hca.slot_service.service.SlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SlotReleaseScheduler {

    private final SlotService slotService;

    @Scheduled(fixedRate = 60000)
    public void releaseExpiredReservations() {

        slotService.releaseExpiredSlots();
    }
}