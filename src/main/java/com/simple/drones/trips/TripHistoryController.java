package com.simple.drones.trips;

import com.simple.drones.exceptions.*;
import com.simple.drones.trips.model.DroneTripsLoadedDTO;
import com.simple.drones.trips.model.TripHistoryDTO;
import com.simple.drones.trips.model.TripLoadRequestDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/trips")
@Controller
@AllArgsConstructor
public class TripHistoryController {
    private final TripHistoryService tripHistoryService;

    @PostMapping("/newTrip")
    private ResponseEntity<TripHistoryDTO> loadMedicineToDone(@RequestBody TripLoadRequestDTO tripLoadRequestDTO) throws TripLocationsException, DroneMaxWeightExceedException, NotFoundException, DroneAlreadyInTripException, BatteryLowException {
        return ResponseEntity.ok().body(tripHistoryService.startLoadingTrip(tripLoadRequestDTO));
    }

    @GetMapping("/checkTripStatus")
    private ResponseEntity<TripHistoryDTO> getTripState(@RequestParam(name = "tripId") long tripId) throws NotFoundException {
        return ResponseEntity.ok().body(tripHistoryService.getLoadedTrip(tripId));
    }

    @GetMapping("/allForADrone")
    private ResponseEntity<DroneTripsLoadedDTO> getAllTripsLoadedForDrone(@RequestParam(name = "droneId") long droneId) throws NotFoundException {
        return ResponseEntity.ok().body(tripHistoryService.findAllTripsLoadedByDroneId(droneId));
    }
}
