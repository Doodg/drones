package com.simple.drones.medicine.model;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

@Mapper(componentModel = "spring")
@Service
public abstract class MedicineMapper {

    public abstract MedicineDTO mapEntityToDTO(MedicineEntity medicineEntity);

    public abstract MedicineEntity mapDTOToEntity(MedicineDTO medicineDTO);
}
