package com.simple.drones;
import com.simple.drones.drones.DronesRepository;
import com.simple.drones.drones.DronesService;
import com.simple.drones.drones.model.*;
import com.simple.drones.exceptions.*;
import com.simple.drones.medicine.MedicineRepository;
import com.simple.drones.medicine.MedicineService;
import com.simple.drones.medicine.model.MedicineDTO;
import com.simple.drones.medicine.model.MedicineEntity;
import com.simple.drones.medicine.model.MedicineMapper;
import com.simple.drones.trips.TripHistoryRepository;
import com.simple.drones.trips.TripHistoryService;
import com.simple.drones.trips.model.*;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TripHistoryServiceTests {

    @InjectMocks
    TripHistoryService tripHistoryService;

    @Mock
    TripHistoryMapper tripHistoryMapper;

    @Mock
    TripHistoryRepository tripHistoryRepository;
    @Mock
    DronesService dronesService;

    @Mock
    DronesRepository dronesRepository;

    @Mock
    DroneMapper droneMapper;
    @Mock
    MedicineService medicineService;

    @Mock
    MedicineRepository medicineRepository;

    @Mock
    MedicineMapper medicineMapper;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testStartLoadingTripWithValidData() {
        String medicineCode = "11LLS";
        String droneSerial = "12355";
        DroneEntity drone = getDroneWithSerial(droneSerial);
        boolean isError = false;
        try {
            when(dronesService.getDroneDetails(org.mockito.ArgumentMatchers.anyLong())).thenReturn(getDtoDrone(drone));
            when(medicineService.getMedicineDetails(org.mockito.ArgumentMatchers.anyString())).thenReturn(getMedicineDto(getMedicineByCode(medicineCode)));
            tripHistoryService.startLoadingTrip(
                    TripLoadRequestDTO.builder()
                            .medicineCode(medicineCode)
                            .droneId(1L)
                            .startPointLocation("FL")
                            .destinationLocation("TX")
                            .build()
            );
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        } catch (TripLocationsException | BatteryLowException | DroneAlreadyInTripException |
                DroneMaxWeightExceedException e) {
            isError = true;
        }
        Assertions.assertFalse(isError);
    }

    @Test
    public void testStartLoadingTripWithBusyDrone() {
        String medicineCode = "11LLS";
        String droneSerial = "12355";
        DroneEntity drone = getDroneWithSerial(droneSerial);
        drone.setState(DroneStateEnum.LOADING);
        boolean isError = false;
        try {
            when(dronesService.getDroneDetails(org.mockito.ArgumentMatchers.anyLong())).thenReturn(getDtoDrone(drone));
            when(medicineService.getMedicineDetails(org.mockito.ArgumentMatchers.anyString())).thenReturn(getMedicineDto(getMedicineByCode(medicineCode)));
            tripHistoryService.startLoadingTrip(
                    TripLoadRequestDTO.builder()
                            .medicineCode(medicineCode)
                            .droneId(1L)
                            .startPointLocation("FL")
                            .destinationLocation("TX")
                            .build()
            );
        } catch (NotFoundException | BatteryLowException | DroneMaxWeightExceedException | TripLocationsException e) {
            throw new RuntimeException(e);
        } catch (DroneAlreadyInTripException e) {
            isError = true;
        }
        Assertions.assertTrue(isError);
    }

    @Test
    public void testStartLoadingTripWithLowBattery() {
        String medicineCode = "11LLS";
        String droneSerial = "12355";
        DroneEntity drone = getDroneWithSerial(droneSerial);
        drone.setBattery(20);
        boolean isError = false;
        try {
            when(dronesService.getDroneDetails(org.mockito.ArgumentMatchers.anyLong())).thenReturn(getDtoDrone(drone));
            when(medicineService.getMedicineDetails(org.mockito.ArgumentMatchers.anyString())).thenReturn(getMedicineDto(getMedicineByCode(medicineCode)));
            tripHistoryService.startLoadingTrip(
                    TripLoadRequestDTO.builder()
                            .medicineCode(medicineCode)
                            .droneId(1L)
                            .startPointLocation("FL")
                            .destinationLocation("TX")
                            .build()
            );
        } catch (TripLocationsException | DroneAlreadyInTripException | NotFoundException |
                DroneMaxWeightExceedException e) {
            throw new RuntimeException(e);
        } catch (BatteryLowException e) {
            isError = true;
        }
        Assertions.assertTrue(isError);
    }

    @Test
    public void testStartLoadingTripWithOverWeight() {
        String medicineCode = "11LLS";
        String droneSerial = "12355";
        DroneEntity drone = getDroneWithSerial(droneSerial);
        MedicineEntity medicine = getMedicineByCode(medicineCode);
        boolean isError = false;
        medicine.setWeight(60);
        try {
            when(dronesService.getDroneDetails(org.mockito.ArgumentMatchers.anyLong())).thenReturn(getDtoDrone(drone));
            when(medicineService.getMedicineDetails(org.mockito.ArgumentMatchers.anyString())).thenReturn(getMedicineDto(medicine));
            tripHistoryService.startLoadingTrip(
                    TripLoadRequestDTO.builder()
                            .medicineCode(medicineCode)
                            .droneId(1L)
                            .startPointLocation("FL")
                            .destinationLocation("TX")
                            .build()
            );
        } catch (TripLocationsException | NotFoundException | DroneAlreadyInTripException | BatteryLowException e) {
            throw new RuntimeException(e);
        } catch (DroneMaxWeightExceedException e) {
            isError = true;
        }
        Assertions.assertTrue(isError);
    }

    @Test
    public void testStartLoadingTripWithInvalidLocations() {
        String medicineCode = "11LLS";
        String droneSerial = "12355";
        DroneEntity drone = getDroneWithSerial(droneSerial);
        MedicineEntity medicine = getMedicineByCode(medicineCode);
        boolean isError = false;
        try {
            when(dronesService.getDroneDetails(org.mockito.ArgumentMatchers.anyLong())).thenReturn(getDtoDrone(drone));
            when(medicineService.getMedicineDetails(org.mockito.ArgumentMatchers.anyString())).thenReturn(getMedicineDto(medicine));
            tripHistoryService.startLoadingTrip(
                    TripLoadRequestDTO.builder()
                            .medicineCode(medicineCode)
                            .droneId(1L)
                            .startPointLocation("FL")
                            .destinationLocation("FL")
                            .build()
            );
        } catch (TripLocationsException e) {
            isError = true;
        } catch (DroneMaxWeightExceedException | NotFoundException | DroneAlreadyInTripException | BatteryLowException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(isError);
    }

    @Test
    public void testFindAllTripsLoadedByDroneIdWithExistingTrips(){
        when(droneMapper.mapDTOToEntity(any())).thenReturn(getDroneWithSerial("12355"));
        when(tripHistoryRepository.findAllByDrone(any())).thenReturn(getAllTripsByDroneSerial("12355"));
        for (TripHistoryEntity trip:
                getAllTripsByDroneSerial("12355")) {
            when(tripHistoryMapper.mapEntityToDTO(any())).thenReturn(getTripHistoryDTO(trip));
        }
        boolean isError = false;
        try{
            DroneTripsLoadedDTO trips = tripHistoryService.findAllTripsLoadedByDroneId(1L);
            Assertions.assertEquals(3, trips.getMedicines().size());
        } catch (NotFoundException e) {
            isError = true;
        }
        Assertions.assertFalse(isError);
    }


    @Test
    public void testFindAllTripsLoadedByDroneIdWithEmptyTrips(){
        when(droneMapper.mapDTOToEntity(any())).thenReturn(getDroneWithSerial("12355"));
        when(tripHistoryRepository.findAllByDrone(any())).thenReturn(new ArrayList<>());
        boolean isError = false;
        try{
            DroneTripsLoadedDTO trips = tripHistoryService.findAllTripsLoadedByDroneId(1L);
            Assertions.assertEquals(3, trips.getMedicines().size());
        } catch (NotFoundException e) {
            isError = true;
        }
        Assertions.assertTrue(isError);
    }

    private TripHistoryDTO getTripHistoryDTO(TripHistoryEntity trip){

        return TripHistoryDTO.builder()
                .id(trip.getId())
                .destinationLocation(trip.getDestinationLocation())
                .drone(getDtoDrone(trip.getDrone()))
                .medicine(getMedicineDto(trip.getMedicine()))
                .startAt(trip.getStartAt())
                .startPointLocation(trip.getStartPointLocation())
                .tripState(trip.getTripState())
                .build();

    }

    private List<TripHistoryEntity> getAllTripsByDroneSerial(String serial){
        List<TripHistoryEntity> trips = new ArrayList<>();
        trips.add(
                TripHistoryEntity.builder()
                        .id(1L)
                        .drone(getDroneWithSerial(serial))
                        .medicine(getMedicineByCode("11SSE"))
                        .tripState(TripStateEnum.DELIVERED)
                        .startPointLocation("FL")
                        .destinationLocation("TX")
                        .startAt(LocalDateTime.now())
                        .build()
        );

        trips.add(
                TripHistoryEntity.builder()
                        .id(2L)
                        .drone(getDroneWithSerial(serial))
                        .medicine(getMedicineByCode("11SSECC"))
                        .tripState(TripStateEnum.DELIVERING)
                        .startPointLocation("FL")
                        .destinationLocation("DC")
                        .startAt(LocalDateTime.now())
                        .build()
        );

        trips.add(
                TripHistoryEntity.builder()
                        .id(3L)
                        .drone(getDroneWithSerial(serial))
                        .medicine(getMedicineByCode("11SZZZW"))
                        .tripState(TripStateEnum.DELIVERED)
                        .startPointLocation("NC")
                        .destinationLocation("NY")
                        .startAt(LocalDateTime.now())
                        .build()
        );
        return trips;
    }

    private DroneEntity getDroneWithSerial(String serial) {
        return DroneEntity.builder()
                .id(1L)
                .battery(95)
                .maxWeight(10)
                .model(DroneModelEnum.CRUISERWEIGHT)
                .serialNumber(serial)
                .state(DroneStateEnum.IDLE)
                .build();
    }

    private DroneDTO getDtoDrone(DroneEntity drone) {
        return DroneDTO.builder()
                .id(drone.getId())
                .battery(drone.getBattery())
                .maxWeight(drone.getMaxWeight())
                .model(drone.getModel())
                .serialNumber(drone.getSerialNumber())
                .state(drone.getState())
                .build();
    }

    private MedicineDTO getMedicineDto(MedicineEntity medicine) {
        return MedicineDTO.builder()
                .code(medicine.getCode())
                .id(medicine.getId())
                .image(medicine.getImage())
                .name(medicine.getName())
                .weight(medicine.getWeight())
                .build();
    }

    private MedicineEntity getMedicineByCode(String code) {
        return MedicineEntity.builder()
                .id(1L)
                .code(code)
                .image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcScL4vtL7n7pWkN7V-VYTvCaWrE2ddUgudfnh2wCUjXfw&s")
                .name("Med-1X")
                .weight(5)
                .build();
    }

}
