package com.example.adriatik.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationPayload {

    private Integer id;
    private Integer userId;


    private Integer tableId;


    private LocalDate reservationDate;


    private LocalTime reservationTime;



}