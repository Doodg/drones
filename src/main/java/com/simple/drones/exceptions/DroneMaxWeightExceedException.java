package com.simple.drones.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DroneMaxWeightExceedException extends BusinessException {
    public static final String DEFAULT_MESSAGE = "The selected medicine weight is greater than drone capacity";

    public DroneMaxWeightExceedException() {
        super(DEFAULT_MESSAGE);
    }
}
