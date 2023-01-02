package com.parkinglot.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ParkingLotExceptionHandler {

   @ExceptionHandler(ParkingLotNotFoundException.class)
    public ResponseEntity<Response> handleParkingLotNotFoundException(ParkingLotNotFoundException e)
   {
       HttpStatus status = HttpStatus.NOT_FOUND;
       Response response=new Response(e.getMessage());
       return new ResponseEntity<Response> (response,status);
   }

}
