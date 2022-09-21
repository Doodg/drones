package com.simple.drones.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotFoundException extends BusinessException {
    public static final String DEFAULT_MESSAGE = "Drone not found";

    public NotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
