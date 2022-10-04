package com.simple.drones.medicine.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MedicineRegisterDTO {

    private String code;
    private String name;
    private double weight;
    private String image;
}
