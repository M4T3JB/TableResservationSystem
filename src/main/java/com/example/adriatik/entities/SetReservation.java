package com.example.adriatik.entities;


import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "set_reservation")
public class SetReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "table_id", nullable = false)
    @NotNull(message = "set_count shouldn't be null")
    private Integer tableNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "reservation_time", nullable = false)
    @NotNull(message = "reservation_time shouldn't be null")
    private LocalDateTime reservationTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id", referencedColumnName = "id")
    private Program program;
}

