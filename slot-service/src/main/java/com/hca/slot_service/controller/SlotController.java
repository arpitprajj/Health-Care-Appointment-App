package com.hca.slot_service.controller;

import com.hca.slot_service.dto.GenerateSlotsRequest;
import com.hca.slot_service.dto.SlotResponse;
import com.hca.slot_service.entity.DoctorSlot;
import com.hca.slot_service.service.SlotService;
import com.hca.slot_service.utility.SlotStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/slots")
@RequiredArgsConstructor
public class SlotController {

    @Autowired
    private final SlotService slotService;

    @GetMapping("/{slotId}")
    public ResponseEntity<SlotResponse>getSlots(@PathVariable UUID slotId){
        return ResponseEntity.ok(slotService.getSlot(slotId));
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<DoctorSlot>>getByDoctorIdAndDate(@PathVariable String doctorId, @RequestParam LocalDate date){
        List<DoctorSlot>slotList=slotService.findByDoctorIdAndSlotDate(doctorId, date);
        return ResponseEntity.ok(slotList);
    }
    @GetMapping("/status/{doctorId}")
    public ResponseEntity<List<DoctorSlot>>getByDoctorIdAndStatus(@PathVariable String doctorId, @RequestParam SlotStatus status){
        List<DoctorSlot>slotList=slotService.findByDoctorIdAndStatus(doctorId, status);
        return ResponseEntity.ok(slotList);
    }


    @PostMapping("/generate")
    public ResponseEntity<Void> generateSlots(
            @RequestBody GenerateSlotsRequest request) {

        slotService.generateSlots(request);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{slotId}/reserve")
    public ResponseEntity<SlotResponse> reserveSlot(
            @PathVariable UUID slotId,
            @RequestParam String patientId,
             @RequestParam UUID appointmentId) {

        return ResponseEntity.ok(
                slotService.reserveSlot(
                        slotId,
                        patientId,appointmentId));
    }

    @PatchMapping("/{slotId}/book")
    public ResponseEntity<SlotResponse> bookSlot(
            @PathVariable UUID slotId,
            @RequestParam UUID appointmentId) {

        return ResponseEntity.ok(
                slotService.bookSlot(slotId,appointmentId));
    }
    @PatchMapping("{slotId}/release")
    public ResponseEntity<SlotResponse>releaseSlot(@PathVariable UUID slotId,@RequestParam UUID appointmentId){
        return ResponseEntity.ok(slotService.releaseSlot(slotId,appointmentId));
    }

}
