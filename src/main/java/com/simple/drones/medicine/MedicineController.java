package com.simple.drones.medicine;

import com.simple.drones.exceptions.InvalidRequestDetails;
import com.simple.drones.exceptions.MedicineAlreadyRegisteredException;
import com.simple.drones.exceptions.NotFoundException;
import com.simple.drones.medicine.model.MedicineDTO;
import com.simple.drones.medicine.model.MedicineRegisterDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/medicine")
@Controller
@AllArgsConstructor
public class MedicineController {
    private final MedicineService medicineService;

    @PostMapping("/newMedicine")
    private ResponseEntity<MedicineDTO> addNewMedicine(@RequestBody MedicineRegisterDTO medicineRegisterDTO) throws InvalidRequestDetails, MedicineAlreadyRegisteredException, NotFoundException {
        return ResponseEntity.ok().body(medicineService.newMedicine(medicineRegisterDTO));
    }

    @GetMapping("/details")
    private ResponseEntity<MedicineDTO> addNewMedicine(@RequestParam(name = "medicineCode") String medicineCode) throws NotFoundException {
        return ResponseEntity.ok().body(medicineService.getMedicineDetails(medicineCode));
    }

}
