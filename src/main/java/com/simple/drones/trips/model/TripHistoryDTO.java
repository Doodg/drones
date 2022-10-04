package com.simple.drones.trips.model;

import com.simple.drones.drones.model.DroneDTO;
import com.simple.drones.drones.model.DroneEntity;
import com.simple.drones.medicine.model.MedicineDTO;
import com.simple.drones.medicine.model.MedicineEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
public class TripHistoryDTO {

    private Long id;
    private DroneDTO drone;
    private MedicineDTO medicine;
    private TripStateEnum tripState;
    private String startPointLocation;
    private String destinationLocation;
    private LocalDateTime startAt;
}
