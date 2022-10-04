package com.simple.drones.jobs;


import com.simple.drones.drones.DronesRepository;
import com.simple.drones.drones.model.DroneEntity;
import com.simple.drones.drones.model.DroneStateEnum;
import com.simple.drones.trips.TripHistoryRepository;
import com.simple.drones.trips.model.TripHistoryEntity;
import com.simple.drones.trips.model.TripStateEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class DeliveryDroneJob {
    @Autowired
    private DronesRepository dronesRepository;
    @Autowired
    private TripHistoryRepository tripHistoryRepository;

    @Scheduled(cron = "0 */2 * ? * *")
    public void getAllDeliveringDrones() {
        List<DroneEntity> dronesList = dronesRepository.findAll().stream().filter(drone ->
                drone.getState() != DroneStateEnum.IDLE
        ).collect(Collectors.toList());
        if (dronesList.isEmpty()) {
            log.info("No drones in operation");
        } else {
            for (DroneEntity droneEntity : dronesList) {
                TripHistoryEntity tripHistoryEntity = tripHistoryRepository.findAllByDroneAndTripStateIs(droneEntity, TripStateEnum.DELIVERING);
                log.info("tripHistoryEntity found for drones id {} in status DELIVERING", droneEntity.getId());
                switch (droneEntity.getState()) {
                    case LOADING:
                        dronesRepository.updateDroneState(droneEntity.getId(), DroneStateEnum.LOADED);
                        log.info("Drone with Id {} Is Become in DELIVERING State", droneEntity.getId());
                        dronesRepository.updateDroneState(droneEntity.getId(), DroneStateEnum.DELIVERING);
                        log.info("drone with id {} status updated from LOADED to DELIVERING", droneEntity.getId());
                        break;
                    case LOADED:
                        dronesRepository.updateDroneState(droneEntity.getId(), DroneStateEnum.DELIVERING);
                        log.info("drone with id {} status updated from LOADED to DELIVERING", droneEntity.getId());
                        break;
                    case DELIVERING:
                        dronesRepository.updateDroneState(droneEntity.getId(), DroneStateEnum.DELIVERED);
                        log.info("drone with id {} status updated from DELIVERING to DELIVERED", droneEntity.getId());
                        tripHistoryRepository.updateTripStatus(tripHistoryEntity.getId(), TripStateEnum.DELIVERED);
                        log.info("tripHistoryEntity updated for drones id {} with status DELIVERED ", droneEntity.getId());
                        break;
                    case DELIVERED:
                        dronesRepository.updateDroneState(droneEntity.getId(), DroneStateEnum.RETURNING);
                        log.info("drone with id {} status updated from DELIVERED to RETURNING", droneEntity.getId());
                        break;
                    case RETURNING:
                        dronesRepository.updateDroneState(droneEntity.getId(), DroneStateEnum.IDLE);
                        log.info("drone with id {} status updated from RETURNING to IDLE", droneEntity.getId());
                        break;

                }
            }
        }
    }
}