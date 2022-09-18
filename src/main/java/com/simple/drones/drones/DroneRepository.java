package com.simple.drones.drones;

import com.simple.drones.drones.model.DroneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DroneRepository extends JpaRepository<DroneEntity, Long> {
}
