package com.hca.appointment_service.exceptions;

public class DoctorServiceUnavailableException
        extends RuntimeException {

    public DoctorServiceUnavailableException(
            String message) {

        super(message);
    }
}
