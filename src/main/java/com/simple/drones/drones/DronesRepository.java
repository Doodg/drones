package com.simple.drones.drones;

import com.simple.drones.drones.model.DroneEntity;
import com.simple.drones.drones.model.DroneStateEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface DronesRepository extends JpaRepository<DroneEntity, Long> {
    Collection<DroneEntity> findAllByStateIs(DroneStateEnum droneStateEnum);

    Optional<DroneEntity> findBySerialNumber(String serialNumber);

    @Modifying
    @Transactional
    @Query("UPDATE drones  d set d.state=:droneStateEnum where d.id=:droneId")
    void updateDroneState(long droneId,DroneStateEnum droneStateEnum);
}
