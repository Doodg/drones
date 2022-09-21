package com.simple.drones.drones;

import com.simple.drones.drones.model.*;
import com.simple.drones.exceptions.DroneAlreadyRegisteredException;
import com.simple.drones.exceptions.InvalidRequestDetails;
import com.simple.drones.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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

    public DroneDTO registerNewDrone(DroneRegisterDTO droneRegisterDTO) throws DroneAlreadyRegisteredException, InvalidRequestDetails {
        DroneDTO drone = droneMapper.mapEntityToDTO(droneRepository.findBySerialNumber(droneRegisterDTO.getSerialNumber()).orElse(null));
        if (drone != null) {
            throw new DroneAlreadyRegisteredException();
        }
        return validateAndSaveDroneDetails(droneRegisterDTO);
    }

    private DroneDTO validateAndSaveDroneDetails(DroneRegisterDTO droneRegisterDTO) throws InvalidRequestDetails {
        if (droneRegisterDTO.getSerialNumber().length() <= 0 || droneRegisterDTO.getSerialNumber().length() > 100) {
            throw new InvalidRequestDetails("Drone Serial Number is invalid");
        }
        if (droneRegisterDTO.getSerialNumber().isEmpty()) {
            throw new InvalidRequestDetails("Drone Serial Number is required");
        }
        if (droneRegisterDTO.getModel().name().isEmpty()) {
            throw new InvalidRequestDetails("Drone Model is required");
        }
        if (droneRegisterDTO.getMaxWeight() <= 0) {
            throw new InvalidRequestDetails("Drone Max Weight is invalid");

        }

        return droneMapper.mapEntityToDTO(droneRepository.save(DroneEntity.builder()
                .battery(100)
                .model(droneRegisterDTO.getModel())
                .maxWeight(droneRegisterDTO.getMaxWeight())
                .serialNumber(droneRegisterDTO.getSerialNumber())
                .state(DroneStateEnum.IDLE).build()));
    }

    public DroneDTO droneBatteryLevel(String droneSerialNumber) throws NotFoundException {
        DroneDTO drone = droneMapper.mapEntityToDTO(droneRepository.findBySerialNumber(droneSerialNumber).orElse(null));
        if (drone == null) {
            throw new NotFoundException();
        }
        return drone;
    }

}
