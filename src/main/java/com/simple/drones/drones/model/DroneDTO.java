package com.simple.drones.drones.model;

import lombok.Data;

@Data
public class DroneDTO {
    private String serialNumber;
    private DroneModelEnum model;
    private int droneWeight;
    private int battery;
    private DoneStateEnum state;

}
