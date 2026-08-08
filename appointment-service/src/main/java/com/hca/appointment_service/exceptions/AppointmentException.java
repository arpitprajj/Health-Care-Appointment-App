package com.hca.appointment_service.exceptions;

public class AppointmentException extends RuntimeException{
    public AppointmentException(String message){
        super(message);
    }
}
