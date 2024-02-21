package com.example.adriatik.services;

import com.example.adriatik.dto.ReservationPayload;
import com.example.adriatik.entities.Reservation;
import com.example.adriatik.repositories.ReservationRepository;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {

    public final  ReservationRepository reservationRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation addReservation(ReservationPayload reservationPayload) throws ParseException {
        Reservation reservation = new Reservation();
        reservation.setTableNumber(reservationPayload.getTableNumber());
        reservation.setTableCapacity(reservationPayload.getTableCapacity());
        reservation.setUser(reservationPayload.getUser());
        reservation.setReservationTime(reservationPayload.getReservationTime());
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation findByTableNumber(Integer tableNumber) {
        return reservationRepository.findByTableNumber(tableNumber);

    }

    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    public boolean existsByNumber(Integer tableNumber) {
        return reservationRepository.existsByTableNumber(tableNumber);
    }

    @Override
    public boolean deleteReservationById(Integer id) {
        if (reservationRepository.existsById(id)) {
            reservationRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Reservation editReservation(Integer id, ReservationPayload reservationPayload) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(id);
        if (reservationOptional.isPresent()) {
            Reservation reservation = reservationOptional.get();
            reservation.setTableNumber(reservationPayload.getTableNumber());
            reservation.setTableCapacity(reservationPayload.getTableCapacity());
            reservation.setUser(reservationPayload.getUser());
            reservation.setReservationTime(reservationPayload.getReservationTime());
            return reservationRepository.save(reservation);
        } else {
            throw new IllegalArgumentException("No reservation found with id: " + id);
        }
    }

    @Override
    public Reservation findById(Integer id) {
        return reservationRepository.findById(id).orElse(null);
    }

    @Override
    public List<Reservation> findAllReservationsOrderedByIdAsc() {
        return reservationRepository.findAllByOrderByIdAsc();
    }
}