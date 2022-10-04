package com.simple.drones.drones;

import com.simple.drones.drones.model.DroneDTO;
import com.simple.drones.drones.model.DroneRegisterDTO;
import com.simple.drones.exceptions.DroneAlreadyRegisteredException;
import com.simple.drones.exceptions.InvalidRequestDetails;
import com.simple.drones.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/drones")
@Controller
@AllArgsConstructor
public class DronesController {
    private final DronesService dronesService;

    @GetMapping("/allAvailable")
    private ResponseEntity<List<DroneDTO>> getAllDrones() {
        return ResponseEntity.ok().body(dronesService.getAllAvailableDrones());
    }

    @PostMapping("/newDrone")
    private ResponseEntity<DroneDTO> addNewDrone(@RequestBody DroneRegisterDTO droneRegisterDTO) throws DroneAlreadyRegisteredException, InvalidRequestDetails {
        return ResponseEntity.ok().body(dronesService.registerNewDrone(droneRegisterDTO));
    }

    @GetMapping("/checkDroneBattery")
    private ResponseEntity<DroneDTO> getDroneBatteryLevel(@RequestParam(name = "droneId") long droneId) throws NotFoundException {
        return ResponseEntity.ok().body(dronesService.droneBatteryLevel(droneId));
    }


}
