package com.simple.drones.trips;

import com.simple.drones.drones.DronesService;
import com.simple.drones.drones.model.*;
import com.simple.drones.exceptions.*;
import com.simple.drones.medicine.MedicineService;
import com.simple.drones.medicine.model.MedicineDTO;
import com.simple.drones.medicine.model.MedicineMapper;
import com.simple.drones.trips.model.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class TripHistoryService {
    private final TripHistoryRepository tripHistoryRepository;
    private final DronesService dronesService;
    private final DroneMapper droneMapper;
    private final MedicineService medicineService;
    private final MedicineMapper medicineMapper;
    private final TripHistoryMapper tripHistoryMapper;

    public TripHistoryDTO startLoadingTrip(TripLoadRequestDTO tripLoadRequestDTO) throws NotFoundException, BatteryLowException, DroneMaxWeightExceedException, DroneAlreadyInTripException, TripLocationsException {
        DroneDTO droneDTO = dronesService.getDroneDetails(tripLoadRequestDTO.getDroneId());
        MedicineDTO medicineDTO = medicineService.getMedicineDetails(tripLoadRequestDTO.getMedicineCode());
        if (!droneDTO.getState().name().equals(DroneStateEnum.IDLE.name())) {
            throw new DroneAlreadyInTripException();
        }
        if (droneDTO.getBattery() < 25) {
            throw new BatteryLowException();
        }
        if (medicineDTO.getWeight() > droneDTO.getMaxWeight()) {
            throw new DroneMaxWeightExceedException();
        }
        if (tripLoadRequestDTO.getDestinationLocation().isEmpty() || tripLoadRequestDTO.getStartPointLocation().isEmpty()) {
            throw new TripLocationsException("trip locations source/target are required");
        }
        if (tripLoadRequestDTO.getDestinationLocation().equals(tripLoadRequestDTO.getStartPointLocation())) {
            throw new TripLocationsException("trip source/target can not be the same");
        }
        TripHistoryEntity tripHistoryEntity = TripHistoryEntity.builder()
                .drone(droneMapper.mapDTOToEntity(droneDTO))
                .medicine(medicineMapper.mapDTOToEntity(medicineDTO))
                .startPointLocation(tripLoadRequestDTO.getStartPointLocation())
                .destinationLocation(tripLoadRequestDTO.getDestinationLocation())
                .tripState(TripStateEnum.DELIVERING)
                .startAt(LocalDateTime.now()).build();
        tripHistoryRepository.save(tripHistoryEntity);
        log.info("A Trip with Id {} Is Created and in DELIVERING State", tripHistoryEntity.getId());
        dronesService.updateDroneState(droneDTO.getId(), DroneStateEnum.LOADING);
        log.info("Drone with Id {} Is Become in LOADING State", droneDTO.getId());


        return tripHistoryMapper.mapEntityToDTO(tripHistoryEntity);

    }

    public TripHistoryDTO getLoadedTrip(Long tripId) throws NotFoundException {
        TripHistoryEntity tripHistoryDTO = tripHistoryRepository.findById(tripId).orElse(null);
        if (tripHistoryDTO == null) {
            throw new NotFoundException("No trip found of id " + tripId);

        }
        return tripHistoryMapper.mapEntityToDTO(tripHistoryDTO);
    }

    public DroneTripsLoadedDTO findAllTripsLoadedByDroneId(long droneId) throws NotFoundException {
        ArrayList<MedicineDTO> medicineDTOS = new ArrayList<>();
        DroneEntity drone = droneMapper.mapDTOToEntity(dronesService.getDroneDetails(droneId));
        List<TripHistoryEntity> tripHistoryEntityList = tripHistoryRepository.findAllByDrone(drone);
        if (tripHistoryEntityList.isEmpty()) {
            throw new NotFoundException("No loaded trips found for drone id " + droneId);
        }
        log.info("Drone with Id {} have a {} trips", drone.getId(), tripHistoryEntityList.size());
        List<TripHistoryDTO> tripsHistory = tripHistoryEntityList.stream().map(tripHistoryMapper::mapEntityToDTO).collect(Collectors.toList());
        tripsHistory.forEach((tripHistoryDTO -> medicineDTOS.add(tripHistoryDTO.getMedicine())));
        return DroneTripsLoadedDTO.builder().drone(droneMapper.mapEntityToDTO(drone)).medicines(medicineDTOS).build();
    }
}
