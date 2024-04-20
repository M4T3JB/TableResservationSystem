package com.example.adriatik.services;

import com.example.adriatik.dto.ReservationPayload;
import com.example.adriatik.entities.Reservation;
import com.example.adriatik.entities.Tables;
import com.example.adriatik.entities.User;
import com.example.adriatik.entities.ReservationTime;
import com.example.adriatik.repositories.ReservationRepository;
import com.example.adriatik.repositories.ReservationTimeRepository;
import com.example.adriatik.repositories.TablesRepository;
import com.example.adriatik.repositories.UserRepository;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final TablesRepository tablesRepository;
    private final UserRepository userRepository;

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository, TablesRepository tablesRepository, UserRepository userRepository, ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.tablesRepository = tablesRepository;
        this.userRepository = userRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public Reservation addReservation(ReservationPayload reservationPayload) throws ParseException {

        Tables table = tablesRepository.findById(reservationPayload.getTableId())
                .orElseThrow(() -> new IllegalArgumentException("No table found with id: " + reservationPayload.getTableId()));

        ReservationTime reservationTime = reservationTimeRepository.findById(reservationPayload.getReservationTime())
                .orElseThrow(() -> new IllegalArgumentException("No reservation time found with id: " + reservationPayload.getReservationTime()));

        if (reservationRepository.existsByTableAndReservationTime(table, reservationTime)) {
            throw new IllegalArgumentException("Table is already reserved at the chosen time.");
        }

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)principal).getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("No user found with username: " + username));

        Reservation reservation = new Reservation();
        reservation.setTable(table);
        reservation.setUser(user);
        reservation.setReservationTime(reservationTime);
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

    public List<Reservation> findReservationsByTableAndDate(Integer tableId, LocalDate date) {
        Tables table = new Tables();
        table.setId(tableId);
        return reservationRepository.findByTableAndReservationDate(table, date);
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

        ReservationTime reservationTime = reservationTimeRepository.findById(reservationPayload.getReservationTime())
                .orElseThrow(() -> new IllegalArgumentException("No reservation time found with id: " + reservationPayload.getReservationTime()));

        if (reservationRepository.existsByTableAndReservationTime(table, reservationTime)) {
            throw new IllegalArgumentException("Table is already reserved at the chosen time.");
        }

        reservation.setTable(table);
        reservation.setReservationTime(reservationTime);
        reservation.setReservationDate(reservationPayload.getReservationDate());

        reservationRepository.save(reservation);
    }


}