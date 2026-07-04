package com.hca.appointment_service.feign;

import com.hca.appointment_service.dto.SlotResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "SLOT-SERVICE")
public interface SlotClient {

    @PatchMapping("/api/slots/{slotId}/reserve")
    SlotResponse reserveSlot(
            @PathVariable UUID slotId,
            @RequestParam String patientId,
            @RequestParam UUID appointmentId);

    @PatchMapping("/api/slots/{slotId}/book")
    SlotResponse bookSlot(
            @PathVariable UUID slotId,
            @RequestParam UUID appointmentId);

    @GetMapping("/api/slots/{slotId}")
    SlotResponse getSlot(@PathVariable UUID slotId);

    @PatchMapping("api/slots/{slotId}/release")
    SlotResponse releaseSlot(@PathVariable UUID slotId,@RequestParam UUID appointmentId);

}