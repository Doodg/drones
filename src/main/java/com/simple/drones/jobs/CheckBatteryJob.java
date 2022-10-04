package com.simple.drones.jobs;


import com.simple.drones.drones.DronesRepository;
import com.simple.drones.drones.model.DroneEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class CheckBatteryJob {
    @Autowired
    private DronesRepository dronesRepository;

    @Scheduled(cron = "0 */1 * ? * *")
    public void checkDronesBattery() {
        List<DroneEntity> dronesList = dronesRepository.findAll();
        if (dronesList.isEmpty()) {
            log.info("no drones in DB for checking batteries");

        } else {
            for (DroneEntity droneEntity : dronesList) {
                log.info("The Battery status of drone #{} is {}%", droneEntity.getId(), droneEntity.getBattery());
            }
        }
    }
}