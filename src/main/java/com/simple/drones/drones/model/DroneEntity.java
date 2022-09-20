package com.simple.drones.drones.model;


import lombok.*;

import javax.persistence.*;

@Entity(name = "drones")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DroneEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "serial_number", unique = true)
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    private DroneModelEnum model;

    @Column(name = "max_weight")
    private double maxWeight;

    private int battery;

    @Enumerated(EnumType.STRING)
    private DroneStateEnum state;
}
