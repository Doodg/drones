package com.simple.drones.drones;

import com.simple.drones.drones.model.*;
import com.simple.drones.exceptions.DroneAlreadyRegisteredException;
import com.simple.drones.exceptions.InvalidRequestDetails;
import com.simple.drones.exceptions.NotFoundException;
import com.simple.drones.medicine.MedicineService;
import com.simple.drones.medicine.model.MedicineDTO;
import com.simple.drones.trips.TripHistoryService;
import com.simple.drones.trips.model.TripHistoryDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class DronesService {
    private final DronesRepository dronesRepository;
    private final DroneMapper droneMapper;
    private final MedicineService medicineService;

    public List<DroneDTO> getAllAvailableDrones() {
        return dronesRepository.findAllByStateIs(DroneStateEnum.IDLE).stream().map(droneMapper::mapEntityToDTO).collect(Collectors.toList());
    }

    public DroneDTO registerNewDrone(DroneRegisterDTO droneRegisterDTO) throws DroneAlreadyRegisteredException, InvalidRequestDetails {
        DroneDTO drone = droneMapper.mapEntityToDTO(dronesRepository.findBySerialNumber(droneRegisterDTO.getSerialNumber()).orElse(null));
        if (drone != null) {
            throw new DroneAlreadyRegisteredException();
        }
        return validateAndSaveDroneDetails(droneRegisterDTO);
    }

    private DroneDTO validateAndSaveDroneDetails(DroneRegisterDTO droneRegisterDTO) throws InvalidRequestDetails {
        if (droneRegisterDTO.getSerialNumber().isEmpty() || droneRegisterDTO.getSerialNumber().length() > 100) {
            throw new InvalidRequestDetails("Drone Serial Number is invalid");
        }
        if (droneRegisterDTO.getModel().name().isEmpty()) {
            throw new InvalidRequestDetails("Drone Model is required");
        }
        if (droneRegisterDTO.getMaxWeight() <= 0 || droneRegisterDTO.getMaxWeight() >= 500) {
            throw new InvalidRequestDetails("Drone Max Weight is invalid");

        }
        log.info("A Drone with serial {}, and model {} is saving ", droneRegisterDTO.getSerialNumber(), droneRegisterDTO.getModel());
        return droneMapper.mapEntityToDTO(dronesRepository.save(DroneEntity.builder()
                .battery(100)
                .model(droneRegisterDTO.getModel())
                .maxWeight(droneRegisterDTO.getMaxWeight())
                .serialNumber(droneRegisterDTO.getSerialNumber())
                .state(DroneStateEnum.IDLE).build()));
    }

    public DroneDTO getDroneDetails(long droneId) throws NotFoundException {
        DroneDTO drone = droneMapper.mapEntityToDTO(dronesRepository.findById(droneId).orElse(null));
        if (drone == null) {
            throw new NotFoundException("Drone Not found");
        }
        return drone;
    }

    public DroneDTO droneBatteryLevel(long droneId) throws NotFoundException {
        return getDroneDetails(droneId);
    }

    public void updateDroneState(long droneId, DroneStateEnum droneStateEnum) {
        log.info("A Drone with id {}, changed State to {}", droneId, droneStateEnum.name());
        dronesRepository.updateDroneState(droneId, droneStateEnum);
    }


}
