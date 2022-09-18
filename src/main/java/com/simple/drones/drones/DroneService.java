package com.simple.drones.drones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DroneService {
    @Autowired
    private DroneRepository droneRepository;


}
