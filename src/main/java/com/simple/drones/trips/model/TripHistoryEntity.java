package com.simple.drones.trips.model;

import com.simple.drones.drones.model.DroneEntity;
import com.simple.drones.medicine.model.MedicineEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "trips_history")
@Builder
@AllArgsConstructor
public class TripHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drones", referencedColumnName = "id")
    private DroneEntity drone;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicines", referencedColumnName = "id")
    private MedicineEntity medicine;


    @Column(name = "start_point_location")
    private String startPointLocation;

    @Column(name = "destination_location")
    private String destinationLocation;
    @Enumerated(EnumType.STRING)
    @Column(name = "trip_state")
    private TripStateEnum tripState;
    @Column(name = "start_at")
    private LocalDateTime startAt;
}
