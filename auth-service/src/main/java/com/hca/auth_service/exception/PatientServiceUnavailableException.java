package com.hca.auth_service.exception;

public class PatientServiceUnavailableException
        extends RuntimeException {

    public PatientServiceUnavailableException(
            String message) {

        super(message);
    }
}