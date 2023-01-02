package com.parkinglot.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ParkingLotNotFoundException extends RuntimeException {
    public ParkingLotNotFoundException(String message)
    {
        super(message);
    }
    public ParkingLotNotFoundException( )
    {

    }
}
