package com.hca.slot_service.exception;


public class SlotNotFoundException extends RuntimeException {
    public SlotNotFoundException(String slotNotFound) {
        super(slotNotFound);
    }
}
