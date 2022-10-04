package com.simple.drones.medicine.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "medicines")
@Builder
@AllArgsConstructor
public class MedicineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String code;
    private String name;
    private double weight;
    private String image;
}
