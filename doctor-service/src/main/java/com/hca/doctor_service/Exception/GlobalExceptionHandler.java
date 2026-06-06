package com.hca.doctor_service.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DoctorNotFoundException.class)
    ResponseEntity<String>doctorNotFoundHandler(DoctorNotFoundException ex){
        String msg=ex.getMessage();
        return  new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    }
}
