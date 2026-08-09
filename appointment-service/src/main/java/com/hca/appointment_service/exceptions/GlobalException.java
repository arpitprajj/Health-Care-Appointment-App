package com.hca.appointment_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(AppointmentNotFoundException.class)
    ResponseEntity<String> appointmentNotFoundHandler(AppointmentNotFoundException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AppointmentException.class)
    ResponseEntity<String> appointmentExceptionHandler(AppointmentException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PatientServiceUnavailableException.class)
    ResponseEntity<String>patientServiceHandler(PatientServiceUnavailableException ex){
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SlotServiceUnavailableException.class)
    ResponseEntity<String>slotServiceHandler(SlotServiceUnavailableException ex){
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DoctorServiceUnavailableException.class)
    ResponseEntity<String>doctorServiceHandler(DoctorServiceUnavailableException ex){
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }
}