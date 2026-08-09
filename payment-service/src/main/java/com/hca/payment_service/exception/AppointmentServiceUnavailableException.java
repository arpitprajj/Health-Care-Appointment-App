package com.hca.payment_service.exception;

public class AppointmentServiceUnavailableException
        extends RuntimeException {

    public AppointmentServiceUnavailableException(
            String message) {

        super(message);
    }
}