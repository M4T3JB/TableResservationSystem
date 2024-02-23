package com.example.adriatik.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "table1")
public class Tables {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "capacity")
    private Integer capacity;
}
