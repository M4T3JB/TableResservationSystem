package com.example.adriatik.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationPayload {

    private Integer id;
    private Integer userId;
    private Integer tableId;
    private LocalDate reservationDate;
    private String reservationTime;



}