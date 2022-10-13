package com.simple.drones;

import com.simple.drones.drones.DronesRepository;
import com.simple.drones.drones.DronesService;
import com.simple.drones.drones.model.*;
import com.simple.drones.exceptions.DroneAlreadyRegisteredException;
import com.simple.drones.exceptions.InvalidRequestDetails;
import com.simple.drones.exceptions.NotFoundException;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DronesServiceTests {

    @InjectMocks
    DronesService dronesService;

    @Mock
    DronesRepository dronesRepository;

    @Mock
    DroneMapper droneMapper;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllAvailableDrones() {
        when(dronesRepository.findAllByStateIs(DroneStateEnum.IDLE)).thenReturn(getIdleDrones());
        for (DroneEntity drone :
                getIdleDrones()) {
            when(droneMapper.mapEntityToDTO(org.mockito.ArgumentMatchers.any())).thenReturn(getDtoDrone(drone));
        }
        List<DroneDTO> drones = dronesService.getAllAvailableDrones();
        Assertions.assertEquals(3, drones.size());
        for (DroneDTO drone :
                drones) {
            Assertions.assertEquals(DroneStateEnum.IDLE, drone.getState());
        }
    }

    @Test
    public void testRegisterNewDroneWithExistingSerial() {
        String serialNumber = "125666";
        when(dronesRepository.findBySerialNumber(serialNumber)).thenReturn(Optional.ofNullable(getDroneWithSerial(serialNumber)));
        when(droneMapper.mapEntityToDTO(org.mockito.ArgumentMatchers.any())).thenReturn(getDtoDrone(getDroneWithSerial(serialNumber)));
        boolean isError = false;
        try {
            dronesService.registerNewDrone(
                    DroneRegisterDTO.builder()
                            .maxWeight(20)
                            .model(DroneModelEnum.CRUISERWEIGHT)
                            .serialNumber(serialNumber)
                            .build()
            );
        } catch (DroneAlreadyRegisteredException ex) {
            isError = true;
        } catch (InvalidRequestDetails e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(isError);
    }

    @Test
    public void testRegisterNewDroneWithNewSerialAndValidData() {
        String serialNumber = "1668889";
        when(dronesRepository.findBySerialNumber(serialNumber)).thenReturn(Optional.ofNullable(null));
        when(droneMapper.mapEntityToDTO(org.mockito.ArgumentMatchers.any())).thenReturn(null);
        boolean isError = false;
        try {
            dronesService.registerNewDrone(
                    DroneRegisterDTO.builder()
                            .maxWeight(20)
                            .model(DroneModelEnum.CRUISERWEIGHT)
                            .serialNumber(serialNumber)
                            .build()
            );
        } catch (DroneAlreadyRegisteredException ex) {
            isError = true;
        } catch (InvalidRequestDetails e) {
            throw new RuntimeException(e);
        }
        Assertions.assertFalse(isError);
    }

    @Test
    public void testRegisterNewDroneWithNewSerialAndInvalidSerial() {
        String serialNumber = "";
        when(dronesRepository.findBySerialNumber(serialNumber)).thenReturn(Optional.ofNullable(null));
        when(droneMapper.mapEntityToDTO(org.mockito.ArgumentMatchers.any())).thenReturn(null);
        boolean isError = false;
        try {
            dronesService.registerNewDrone(
                    DroneRegisterDTO.builder()
                            .maxWeight(20)
                            .model(DroneModelEnum.CRUISERWEIGHT)
                            .serialNumber(serialNumber)
                            .build()
            );
        } catch (DroneAlreadyRegisteredException ex) {
            throw new RuntimeException();
        } catch (InvalidRequestDetails e) {
            isError = true;
        }
        Assertions.assertTrue(isError);
    }

    @Test
    public void testRegisterNewDroneWithNewSerialAndInvalidMaxWeight() {
        String serialNumber = "255548666";
        when(dronesRepository.findBySerialNumber(serialNumber)).thenReturn(Optional.ofNullable(null));
        when(droneMapper.mapEntityToDTO(org.mockito.ArgumentMatchers.any())).thenReturn(null);
        boolean isError = false;
        try {
            dronesService.registerNewDrone(
                    DroneRegisterDTO.builder()
                            .maxWeight(800)
                            .model(DroneModelEnum.CRUISERWEIGHT)
                            .serialNumber(serialNumber)
                            .build()
            );
        } catch (DroneAlreadyRegisteredException ex) {
            throw new RuntimeException();
        } catch (InvalidRequestDetails e) {
            isError = true;
        }
        Assertions.assertTrue(isError);
    }

    @Test
    public void testGetExistingDroneDetails() {
        String serialNumber = "125666";
        DroneEntity drone = getDroneWithSerial(serialNumber);
        when(dronesRepository.findById(drone.getId())).thenReturn(Optional.ofNullable(drone));
        when(droneMapper.mapEntityToDTO(org.mockito.ArgumentMatchers.any())).thenReturn(getDtoDrone(drone));
        boolean isError = false;
        try {
            dronesService.getDroneDetails(drone.getId());
        } catch (NotFoundException e) {
            isError = true;
        }
        Assertions.assertFalse(isError);
    }

    @Test
    public void testGetNonExistingDroneDetails() {
        String serialNumber = "125666";
        DroneEntity drone = getDroneWithSerial(serialNumber);
        when(dronesRepository.findById(drone.getId())).thenReturn(Optional.ofNullable(null));
        when(droneMapper.mapEntityToDTO(org.mockito.ArgumentMatchers.any())).thenReturn(null);
        boolean isError = false;
        try {
            dronesService.getDroneDetails(drone.getId());
        } catch (NotFoundException e) {
            isError = true;
        }
        Assertions.assertTrue(isError);
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

    private List<DroneEntity> getIdleDrones() {
        List<DroneEntity> drones = new ArrayList<>();
        drones.add(
                DroneEntity.builder()
                        .id(1L)
                        .battery(95)
                        .maxWeight(10)
                        .model(DroneModelEnum.CRUISERWEIGHT)
                        .serialNumber("125666")
                        .state(DroneStateEnum.IDLE)
                        .build()
        );

        drones.add(
                DroneEntity.builder()
                        .id(2L)
                        .battery(80)
                        .maxWeight(20)
                        .model(DroneModelEnum.HEAVYWEIGHT)
                        .serialNumber("125667")
                        .state(DroneStateEnum.IDLE)
                        .build()
        );

        drones.add(
                DroneEntity.builder()
                        .id(3L)
                        .battery(75)
                        .maxWeight(10)
                        .model(DroneModelEnum.LIGHTWEIGHT)
                        .serialNumber("125668")
                        .state(DroneStateEnum.IDLE)
                        .build()
        );
        return drones;
    }

}
