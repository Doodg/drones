package com.simple.drones.medicine.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Builder
@Data
public class MedicineDTO {

    private Long id;
    private String code;
    private String name;
    private double weight;
    private String image;
}
