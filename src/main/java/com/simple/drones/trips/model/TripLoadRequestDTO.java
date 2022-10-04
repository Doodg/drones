package com.simple.drones.trips.model;

import com.simple.drones.drones.model.DroneEntity;
import com.simple.drones.medicine.model.MedicineEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
public class TripLoadRequestDTO {
    private long droneId;
    private String medicineCode;
    private String startPointLocation;
    private String destinationLocation;
}
