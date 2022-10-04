package com.simple.drones.medicine;

import com.simple.drones.medicine.model.MedicineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicineRepository extends JpaRepository<MedicineEntity, Long> {

    Optional<MedicineEntity> findByCode(String medicineCode);


}
