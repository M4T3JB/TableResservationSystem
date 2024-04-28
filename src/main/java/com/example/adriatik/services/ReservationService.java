package com.example.adriatik.services;

import com.example.adriatik.dto.ReservationPayload;
import com.example.adriatik.entities.Reservation;
import com.example.adriatik.entities.ReservationTime;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

public interface ReservationService {
    Reservation addReservation(ReservationPayload reservationPayload) throws ParseException;
    
    boolean deleteReservationById(Integer id);

    List<Reservation> findAllReservationsOrderedByIdAsc();

    Reservation findById(Integer id);

    List<Reservation> findAllReservationsByUser(String username);

    void editReservation(Integer id, ReservationPayload reservationPayload);

    List<Reservation> findReservationsByTableAndDate(Integer tableId, LocalDate date);

    List<Reservation> findReservationsByDateAndTime( LocalDate date, ReservationTime reservationTime);
}