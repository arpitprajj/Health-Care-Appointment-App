package com.hca.appointment_service.exceptions;

public class SlotServiceUnavailableException
        extends RuntimeException {

    public SlotServiceUnavailableException(String message) {
        super(message);
    }
}