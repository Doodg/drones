package com.simple.drones.drones.model;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
public class DroneEntity {
    @Id
    @Column(name = "serial_number", unique = true)
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    private DroneModelEnum model;

    @Column(name = "serial_weight")
    private int droneWeight;

    private int battery;

    @Enumerated(EnumType.STRING)
    private DoneStateEnum state;
}
