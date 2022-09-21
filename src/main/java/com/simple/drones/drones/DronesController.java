package com.simple.drones.drones;

import com.simple.drones.drones.model.DroneDTO;
import com.simple.drones.drones.model.DroneRegisterDTO;
import com.simple.drones.exceptions.DroneAlreadyRegisteredException;
import com.simple.drones.exceptions.InvalidRequestDetails;
import com.simple.drones.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/drones")
@Controller
@AllArgsConstructor
public class DronesController {
    private final DroneService droneService;

    @GetMapping("/allAvailable")
    private ResponseEntity<List<DroneDTO>> getAllDrones() {
        return ResponseEntity.ok().body(droneService.getAllAvailableDrones());
    }

    @PostMapping("/newDrone")
    private ResponseEntity<DroneDTO> addNewDrone(@RequestBody DroneRegisterDTO droneRegisterDTO) throws DroneAlreadyRegisteredException, InvalidRequestDetails {
        return ResponseEntity.ok().body(droneService.registerNewDrone(droneRegisterDTO));
    }

    @GetMapping("/checkDroneBattery")
    private ResponseEntity<DroneDTO> getDroneBatteryLevel(@RequestParam(name = "droneSerialNumber") String droneSerialNumber) throws NotFoundException {
        return ResponseEntity.ok().body(droneService.droneBatteryLevel(droneSerialNumber));
    }


}
