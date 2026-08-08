package com.hca.slot_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;



@ControllerAdvice
public class GlobalSlotExceptionHandler {

    @ExceptionHandler(SlotNotFoundException.class)
    ResponseEntity<String>handleSlotNotFound(SlotNotFoundException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SlotException.class)
    ResponseEntity<String>handleSlotException(SlotException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(FeignException.class)
//    ResponseEntity<String>handleSlotException(SlotException ex){
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
//    }


}
