package com.simple.drones.drones.model;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.Mapping;

@Mapper(componentModel = "spring")
@Service
public abstract class DroneMapper {

    public abstract DroneDTO mapEntityToDTO(DroneEntity droneEntity);
}
