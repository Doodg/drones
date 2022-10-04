package com.simple.drones.drones.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DroneDTO {
    private long id ;
    private String serialNumber;
    private DroneModelEnum model;
    private int maxWeight;
    private int battery;
    private DroneStateEnum state;

}
