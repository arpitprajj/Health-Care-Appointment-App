package com.hca.appointment_service.service.client;

import com.hca.appointment_service.dto.SlotResponse;
import com.hca.appointment_service.exceptions.SlotServiceUnavailableException;
import com.hca.appointment_service.feign.SlotClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
@RequiredArgsConstructor
@Slf4j
public class SlotClientService {

    private final SlotClient slotClient;

    @CircuitBreaker(
            name = "slotService",
            fallbackMethod = "getSlotFallback")
    public SlotResponse getSlot(UUID slotId) {

        return slotClient.getSlot(slotId);
    }

    @CircuitBreaker(
            name = "slotService",
            fallbackMethod = "reserveSlotFallback")
    public SlotResponse reserveSlot(
            UUID slotId,
            String patientId,
            UUID appointmentId) {

        return slotClient.reserveSlot(
                slotId,
                patientId,
                appointmentId);
    }

    @CircuitBreaker(
            name = "slotService",
            fallbackMethod = "bookSlotFallback")
    public SlotResponse bookSlot(
            UUID slotId,
            UUID appointmentId) {

        return slotClient.bookSlot(
                slotId,
                appointmentId);
    }

    @CircuitBreaker(
            name = "slotService",
            fallbackMethod = "releaseSlotFallback")
    public SlotResponse releaseSlot(
            UUID slotId,
            UUID appointmentId) {

        return slotClient.releaseSlot(
                slotId,
                appointmentId);
    }


    private SlotResponse getSlotFallback(
            UUID slotId,
            Throwable throwable) {
        log.error(
                "CIRCUIT BREAKER FALLBACK - Slot Service unavailable. cause={}",
                throwable.toString());
        throw new SlotServiceUnavailableException(
                "Slot service is currently unavailable.");
    }


    private SlotResponse reserveSlotFallback(
            UUID slotId,
            String patientId,
            UUID appointmentId,
            Throwable throwable) {
        log.error(
                "CIRCUIT BREAKER FALLBACK - Slot Service unavailable. cause={}",
                throwable.toString());

        throw new SlotServiceUnavailableException(
                "Unable to reserve slot because "
                        + "Slot Service is unavailable.");
    }


    private SlotResponse bookSlotFallback(
            UUID slotId,
            UUID appointmentId,
            Throwable throwable) {
        log.error(
                "CIRCUIT BREAKER FALLBACK - Slot Service unavailable. cause={}",
                throwable.toString());

        throw new SlotServiceUnavailableException(
                "Unable to book slot because "
                        + "Slot Service is unavailable.");
    }


    private SlotResponse releaseSlotFallback(
            UUID slotId,
            UUID appointmentId,
            Throwable throwable) {
        log.error(
                "CIRCUIT BREAKER FALLBACK - Slot Service unavailable. cause={}",
                throwable.toString());

        throw new SlotServiceUnavailableException(
                "Unable to release slot because "
                        + "Slot Service is unavailable.");
    }
}