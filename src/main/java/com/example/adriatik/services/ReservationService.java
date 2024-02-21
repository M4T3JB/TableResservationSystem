package com.example.adriatik.services;

import com.example.adriatik.dto.ReservationPayload;
import com.example.adriatik.entities.Reservation;

import java.text.ParseException;
import java.util.List;

public interface ReservationService {
    Reservation addReservation(ReservationPayload reservationPayload) throws ParseException;
    Reservation findByTableNumber(Integer tableNumber);
    List<Reservation> findAll();
    boolean existsByNumber(Integer tableNumber);
    boolean deleteReservationById(Integer id);
    Reservation editReservation(Integer id, ReservationPayload reservationPayload);
    Reservation findById(Integer id);
    List<Reservation> findAllReservationsOrderedByIdAsc();
}