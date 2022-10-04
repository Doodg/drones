package com.simple.drones.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BatteryLowException extends BusinessException {
    public static final String DEFAULT_MESSAGE = "Drone can not used due to battery level lower than 25%";

    public BatteryLowException() {
        super(DEFAULT_MESSAGE);
    }
}
