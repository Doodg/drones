package com.simple.drones.drones;

import com.simple.drones.drones.model.DroneDTO;
import com.simple.drones.drones.model.DroneMapper;
import com.simple.drones.drones.model.DroneStateEnum;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DroneService {
    private final DroneRepository droneRepository;
    private final DroneMapper droneMapper;

    public List<DroneDTO> getAllAvailableDrones() {
        return droneRepository.findAllByStateIs(DroneStateEnum.IDLE).stream().map(droneMapper::mapEntityToDTO).collect(Collectors.toList());
    }

}
