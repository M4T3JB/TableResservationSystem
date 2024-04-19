package com.example.adriatik.entities;


import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDate;


@Entity
@Data
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "table_id", nullable = false)
    private Tables table;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "time_id", nullable = false)
    private ReservationTime reservationTime;

    @Column(name = "date", nullable = false)
    private LocalDate reservationDate;



}
