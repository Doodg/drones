package com.simple.drones.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DroneAlreadyInTripException extends BusinessException {
    public static final String DEFAULT_MESSAGE = "Drone Already on a trip, select another drone";

    public DroneAlreadyInTripException() {
        super(DEFAULT_MESSAGE);
    }
}
