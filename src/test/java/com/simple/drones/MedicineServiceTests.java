package com.simple.drones;

import com.simple.drones.exceptions.InvalidRequestDetails;
import com.simple.drones.exceptions.MedicineAlreadyRegisteredException;
import com.simple.drones.exceptions.NotFoundException;
import com.simple.drones.medicine.MedicineRepository;
import com.simple.drones.medicine.MedicineService;
import com.simple.drones.medicine.model.MedicineDTO;
import com.simple.drones.medicine.model.MedicineEntity;
import com.simple.drones.medicine.model.MedicineMapper;
import com.simple.drones.medicine.model.MedicineRegisterDTO;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.util.Optional;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class MedicineServiceTests {


    @InjectMocks
    MedicineService medicineService;

    @Mock
    MedicineRepository medicineRepository;

    @Mock
    MedicineMapper medicineMapper;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test()
    public void testNewMedicineWithExistingCode(){
        String medCode = "125lls";
        MedicineEntity medicine = getMedicineByCode(medCode);
        when(medicineRepository.findByCode(medicine.getCode())).thenReturn(Optional.ofNullable(medicine));
        when(medicineMapper.mapEntityToDTO(org.mockito.ArgumentMatchers.any())).thenReturn(getMedicineDto(medicine));
        boolean isError = false;
        try {
            medicineService.newMedicine(
                    MedicineRegisterDTO.builder()
                            .code(medicine.getCode())
                            .image(medicine.getImage())
                            .name(medicine.getName())
                            .weight(medicine.getWeight())
                            .build()
            );
        }catch (MedicineAlreadyRegisteredException e){
            isError = true;
        } catch (InvalidRequestDetails | NotFoundException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(isError);
    }

    @Test
    public void testNewMedicineWithNewCode(){
        String medCode = "125LLS";
        MedicineEntity medicine = getMedicineByCode(medCode);
        when(medicineRepository.findByCode(medicine.getCode())).thenReturn(Optional.ofNullable(null));
        when(medicineMapper.mapEntityToDTO(org.mockito.ArgumentMatchers.any())).thenReturn(null);
        boolean isError = false;
        try {
            medicineService.newMedicine(
                    MedicineRegisterDTO.builder()
                            .code(medicine.getCode())
                            .image(medicine.getImage())
                            .name(medicine.getName())
                            .weight(medicine.getWeight())
                            .build()
            );
        }catch (MedicineAlreadyRegisteredException | InvalidRequestDetails e){
            isError = true;
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertFalse(isError);
    }

    @Test
    public void testNewMedicineWithInvalidCode(){
        String medCode = "125LL*";
        MedicineEntity medicine = getMedicineByCode(medCode);
        when(medicineRepository.findByCode(medicine.getCode())).thenReturn(Optional.ofNullable(null));
        when(medicineMapper.mapEntityToDTO(org.mockito.ArgumentMatchers.any())).thenReturn(null);
        boolean isError = false;
        try {
            medicineService.newMedicine(
                    MedicineRegisterDTO.builder()
                            .code(medicine.getCode())
                            .image(medicine.getImage())
                            .name(medicine.getName())
                            .weight(medicine.getWeight())
                            .build()
            );
        }catch (InvalidRequestDetails e){
            isError = true;
        } catch (NotFoundException | MedicineAlreadyRegisteredException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(isError);
    }

    @Test
    public void testNewMedicineWithInvalidName(){
        String medCode = "125LLS";
        MedicineEntity medicine = getMedicineByCode(medCode);
        when(medicineRepository.findByCode(medicine.getCode())).thenReturn(Optional.ofNullable(null));
        when(medicineMapper.mapEntityToDTO(org.mockito.ArgumentMatchers.any())).thenReturn(null);
        boolean isError = false;
        try {
            medicineService.newMedicine(
                    MedicineRegisterDTO.builder()
                            .code(medicine.getCode())
                            .image(medicine.getImage())
                            .name("SS4)(&7")
                            .weight(medicine.getWeight())
                            .build()
            );
        }catch (InvalidRequestDetails e){
            isError = true;
        } catch (NotFoundException | MedicineAlreadyRegisteredException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(isError);
    }

    @Test
    public void testNewMedicineWithInvalidImageUrl(){
        String medCode = "125LLS";
        MedicineEntity medicine = getMedicineByCode(medCode);
        when(medicineRepository.findByCode(medicine.getCode())).thenReturn(Optional.ofNullable(null));
        when(medicineMapper.mapEntityToDTO(org.mockito.ArgumentMatchers.any())).thenReturn(null);
        boolean isError = false;
        try {
            medicineService.newMedicine(
                    MedicineRegisterDTO.builder()
                            .code(medicine.getCode())
                            .image("image url")
                            .name(medicine.getName())
                            .weight(medicine.getWeight())
                            .build()
            );
        }catch (InvalidRequestDetails | MedicineAlreadyRegisteredException e){
            throw new RuntimeException(e);
        } catch (NotFoundException e) {
            isError = true;
        }
        Assertions.assertTrue(isError);
    }

    @Test
    public void testNewMedicineWithInvalidWeight(){
        String medCode = "125LLS";
        MedicineEntity medicine = getMedicineByCode(medCode);
        when(medicineRepository.findByCode(medicine.getCode())).thenReturn(Optional.ofNullable(null));
        when(medicineMapper.mapEntityToDTO(org.mockito.ArgumentMatchers.any())).thenReturn(null);
        boolean isError = false;
        try {
            medicineService.newMedicine(
                    MedicineRegisterDTO.builder()
                            .code(medicine.getCode())
                            .image(medicine.getImage())
                            .name(medicine.getName())
                            .weight(0)
                            .build()
            );
        }catch (InvalidRequestDetails e){
            isError = true;
        } catch (NotFoundException | MedicineAlreadyRegisteredException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(isError);
    }

    @Test
    public void testGetExistingMedicineDetails(){
        String medCode = "125llS";
        MedicineEntity medicine = getMedicineByCode(medCode);
        when(medicineRepository.findByCode(medicine.getCode())).thenReturn(Optional.ofNullable(medicine));
        when(medicineMapper.mapEntityToDTO(org.mockito.ArgumentMatchers.any())).thenReturn(getMedicineDto(medicine));
        boolean isError = false;
        try{
            medicineService.getMedicineDetails(medicine.getCode());
        }catch (NotFoundException e){
            isError = true;
        }
        Assertions.assertFalse(isError);
    }

    @Test
    public void testGetNonExistingMedicineDetails(){
        String medCode = "125llS";
        MedicineEntity medicine = getMedicineByCode(medCode);
        when(medicineRepository.findByCode(medicine.getCode())).thenReturn(Optional.ofNullable(null));
        when(medicineMapper.mapEntityToDTO(org.mockito.ArgumentMatchers.any())).thenReturn(null);
        boolean isError = false;
        try{
            medicineService.getMedicineDetails(medicine.getCode());
        }catch (NotFoundException e){
            isError = true;
        }
        Assertions.assertTrue(isError);
    }

    private MedicineDTO getMedicineDto(MedicineEntity medicine){
        return MedicineDTO.builder()
                .code(medicine.getCode())
                .id(medicine.getId())
                .image(medicine.getImage())
                .name(medicine.getName())
                .weight(medicine.getWeight())
                .build();
    }

    private MedicineEntity getMedicineByCode(String code){
        return MedicineEntity.builder()
                .id(1L)
                .code(code)
                .image("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcScL4vtL7n7pWkN7V-VYTvCaWrE2ddUgudfnh2wCUjXfw&s")
                .name("Med-1X")
                .weight(5)
                .build();
    }

}
