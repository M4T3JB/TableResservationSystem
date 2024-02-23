package com.example.adriatik.services;

import com.example.adriatik.dto.ReservationPayload;
import com.example.adriatik.entities.Reservation;
import com.example.adriatik.entities.Tables;
import com.example.adriatik.entities.User;
import com.example.adriatik.repositories.ReservationRepository;
import com.example.adriatik.repositories.TablesRepository;
import com.example.adriatik.repositories.UserRepository;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final TablesRepository tablesRepository;
    private final UserRepository userRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository, TablesRepository tablesRepository, UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.tablesRepository = tablesRepository;
        this.userRepository = userRepository;
    }

    public Reservation addReservation(ReservationPayload reservationPayload) throws ParseException {

        Tables table = tablesRepository.findById(reservationPayload.getTableId())
                .orElseThrow(() -> new IllegalArgumentException("No table found with id: " + reservationPayload.getTableId()));

        if (reservationRepository.existsByTableAndReservationTime(table, reservationPayload.getReservationTime())) {
            throw new IllegalArgumentException("Table is already reserved at the chosen time.");
        }

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)principal).getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("No user found with username: " + username));

        Reservation reservation = new Reservation();
        reservation.setTable(table);
        reservation.setUser(user);
        reservation.setReservationTime(reservationPayload.getReservationTime());
        reservation.setReservationDate(reservationPayload.getReservationDate());

        return reservationRepository.save(reservation);
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
    public List<Reservation> findAllReservationsOrderedByIdAsc() {
        return reservationRepository.findAllByOrderByIdAsc();
    }


    @Override
    public Reservation findById(Integer id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No reservation found with id: " + id));
    }

    @Override
    public List<Reservation> findAllReservationsByUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("No user found with username: " + username));
        return reservationRepository.findAllByUser(user);
    }

    @Override
    public void editReservation(Integer id, ReservationPayload reservationPayload) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No reservation found with id: " + id));

        Tables table = tablesRepository.findById(reservationPayload.getTableId())
                .orElseThrow(() -> new IllegalArgumentException("No table found with id: " + reservationPayload.getTableId()));

        if (reservationRepository.existsByTableAndReservationTime(table, reservationPayload.getReservationTime())) {
            throw new IllegalArgumentException("Table is already reserved at the chosen time.");
        }

        reservation.setTable(table);
        reservation.setReservationTime(reservationPayload.getReservationTime());
        reservation.setReservationDate(reservationPayload.getReservationDate());

        reservationRepository.save(reservation);
    }


}