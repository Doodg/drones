package com.simple.drones.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DroneAlreadyRegisteredException extends BusinessException {
    public static final String DEFAULT_MESSAGE = "Drone Already Registered";

    public DroneAlreadyRegisteredException() {
        super(DEFAULT_MESSAGE);
    }
}
