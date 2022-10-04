package com.simple.drones.medicine;

import com.simple.drones.exceptions.InvalidRequestDetails;
import com.simple.drones.exceptions.MedicineAlreadyRegisteredException;
import com.simple.drones.exceptions.NotFoundException;
import com.simple.drones.medicine.model.MedicineDTO;
import com.simple.drones.medicine.model.MedicineEntity;
import com.simple.drones.medicine.model.MedicineMapper;
import com.simple.drones.medicine.model.MedicineRegisterDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

@Slf4j
@Service
@AllArgsConstructor
public class MedicineService {
    private final MedicineRepository medicineRepository;
    private final MedicineMapper medicineMapper;


    public MedicineDTO newMedicine(MedicineRegisterDTO medicineRegisterDTO) throws MedicineAlreadyRegisteredException, InvalidRequestDetails, NotFoundException {
        MedicineDTO medicineDTO = medicineMapper.mapEntityToDTO(medicineRepository.findByCode(medicineRegisterDTO.getCode()).orElse(null));
        if (medicineDTO != null) {
            throw new MedicineAlreadyRegisteredException();
        }
        return validateMedicineDetails(medicineRegisterDTO);
    }

    private MedicineDTO validateMedicineDetails(MedicineRegisterDTO medicineRegisterDTO) throws InvalidRequestDetails, NotFoundException {
        if (medicineRegisterDTO.getName().isEmpty())
            throw new InvalidRequestDetails("Medicine Name is required");
        if (!Pattern.matches("^[A-Za-z0-9-_]+$", medicineRegisterDTO.getName()))
            throw new InvalidRequestDetails("Medicine Name only accept letters, numbers, ‘-‘, ‘_’");
        if (medicineRegisterDTO.getCode().isEmpty())
            throw new InvalidRequestDetails("Medicine Code is required");
        if (!Pattern.matches("^[A-Z0-9_]+$", medicineRegisterDTO.getCode()))
            throw new InvalidRequestDetails("Medicine Code only accept UPPERCASE letters, underscore and numbers");
        if (medicineRegisterDTO.getImage().isEmpty())
            throw new InvalidRequestDetails("Medicine Image is required");
        validateImageUrl(medicineRegisterDTO.getImage());
        if (medicineRegisterDTO.getWeight() <= 0)
            throw new InvalidRequestDetails("Medicine Weight is required");


        log.info("A Medicine with Name {} and Code {} is saving", medicineRegisterDTO.getName(), medicineRegisterDTO.getCode());
        return medicineMapper.mapEntityToDTO(medicineRepository.save(MedicineEntity.builder()
                .name(medicineRegisterDTO.getName())
                .code(medicineRegisterDTO.getCode())
                .weight(medicineRegisterDTO.getWeight())
                .image(medicineRegisterDTO.getImage()).build()));
    }

    private void validateImageUrl(String imageUrl) throws InvalidRequestDetails, NotFoundException {
        Image image;
        try {
            image = ImageIO.read(new URL(imageUrl));
            if (image == null) {
                throw new InvalidRequestDetails("Medicine Image url is invalid");
            }
        } catch (IOException ioException) {
            throw new NotFoundException("Image url is invalid");

        }

    }

    public MedicineDTO getMedicineDetails(String medicineCode) throws NotFoundException {
        MedicineDTO medicineDTO = medicineMapper.mapEntityToDTO(medicineRepository.findByCode(medicineCode).orElse(null));
        if (medicineDTO == null) {
            throw new NotFoundException("Medicine Not found");
        }
        return medicineDTO;
    }
}
