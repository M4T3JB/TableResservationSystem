package com.example.adriatik.entities;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "reservation_time")
public class ReservationTime{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "time", nullable = false)
    private String time;


}
