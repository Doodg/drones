package com.simple.drones.medicine;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "")
@Getter
@Setter
@NoArgsConstructor
public class MedicineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String code;
    private String name;
    private double weight;
    private String image;
}
