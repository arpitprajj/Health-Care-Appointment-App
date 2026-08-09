package com.hca.appointment_service.exceptions;

public class PatientServiceUnavailableException extends RuntimeException{
    public PatientServiceUnavailableException(String message){
        super(message);
    }
}
