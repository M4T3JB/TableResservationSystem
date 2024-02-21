package com.example.adriatik.dto;

import com.example.adriatik.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationPayload {

    private int tableNumber;


    private int tableCapacity;


    private User user;


    private LocalDateTime reservationTime;
}