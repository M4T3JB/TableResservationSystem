package com.example.adriatik.services;

import com.example.adriatik.entities.SetReservation;

public interface SetReservationService {
    SetReservation addSetReservation(SetReservation setReservation);
    boolean deleteSetReservationById(Integer id);
    SetReservation editSetReservation(Integer id, SetReservation setReservation);
    SetReservation findById(Integer id);
}