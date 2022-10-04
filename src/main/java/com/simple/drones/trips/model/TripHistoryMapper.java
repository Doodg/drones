package com.simple.drones.trips.model;

import com.simple.drones.medicine.model.MedicineDTO;
import com.simple.drones.medicine.model.MedicineEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

@Mapper(componentModel = "spring")
@Service
public abstract class TripHistoryMapper {

    public abstract TripHistoryDTO mapEntityToDTO(TripHistoryEntity tripHistoryEntity);

}
