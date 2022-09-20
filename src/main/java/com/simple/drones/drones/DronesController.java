package com.simple.drones.drones;

import com.simple.drones.drones.model.DroneDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
