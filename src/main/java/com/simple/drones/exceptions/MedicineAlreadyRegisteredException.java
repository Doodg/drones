package com.simple.drones.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MedicineAlreadyRegisteredException extends BusinessException {
    public static final String DEFAULT_MESSAGE = "Medicine Already Registered";

    public MedicineAlreadyRegisteredException() {
        super(DEFAULT_MESSAGE);
    }
}
