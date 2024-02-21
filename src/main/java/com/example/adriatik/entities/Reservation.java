package com.example.adriatik.entities;


import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "tableNumber", nullable = false)
    @NotNull(message = "Value cant be null")
    private int tableNumber;

    @Column(name = "tableCapacity", nullable = false)
    @NotNull(message = "Value cant be null")
    private int tableCapacity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "Value cant be null")
    private User user;

    @Column(name = "reservation_time", nullable = false)
    @NotNull(message = "Value cant be null")
    private LocalDateTime reservationTime;


}
