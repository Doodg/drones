package com.simple.drones.trips.model;

import com.simple.drones.drones.model.DroneDTO;
import com.simple.drones.medicine.model.MedicineDTO;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

@Data
@Builder
public class DroneTripsLoadedDTO {
    private DroneDTO drone;
    private ArrayList<MedicineDTO> medicines;
}
