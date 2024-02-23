package com.example.adriatik.services;

import com.example.adriatik.dto.ReservationPayload;
import com.example.adriatik.entities.Reservation;

import java.text.ParseException;
import java.util.List;

public interface ReservationService {
    Reservation addReservation(ReservationPayload reservationPayload) throws ParseException;
    
    boolean deleteReservationById(Integer id);

    List<Reservation> findAllReservationsOrderedByIdAsc();

    Reservation findById(Integer id);

    List<Reservation> findAllReservationsByUser(String username);

    void editReservation(Integer id, ReservationPayload reservationPayload);
}