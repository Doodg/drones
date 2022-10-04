package com.simple.drones.trips;

import com.simple.drones.drones.model.DroneEntity;
import com.simple.drones.trips.model.TripHistoryEntity;
import com.simple.drones.trips.model.TripStateEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TripHistoryRepository extends JpaRepository<TripHistoryEntity, Long> {
    List<TripHistoryEntity> findAllByDrone(DroneEntity drone);

    TripHistoryEntity findAllByDroneAndTripStateIs(DroneEntity drone, TripStateEnum tripStateEnum);

    @Transactional
    @Modifying
    @Query(value = "UPDATE trips_history  trip set trip.tripState=:tripState where trip.id=:tripId")
    void updateTripStatus(long tripId, TripStateEnum tripState);
}
