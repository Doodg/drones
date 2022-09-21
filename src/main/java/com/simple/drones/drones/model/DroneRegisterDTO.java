package com.simple.drones.drones.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DroneRegisterDTO {
    private String serialNumber;
    private DroneModelEnum model;
    private int maxWeight;
}
