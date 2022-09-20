package com.simple.drones.drones;

import com.simple.drones.drones.model.DroneEntity;
import com.simple.drones.drones.model.DroneStateEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface DroneRepository extends JpaRepository<DroneEntity, Long> {
    Collection<DroneEntity> findAllByStateIs(DroneStateEnum droneStateEnum);
}
