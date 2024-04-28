
package com.example.adriatik.repositories;


import com.example.adriatik.entities.Reservation;
import com.example.adriatik.entities.ReservationTime;
import com.example.adriatik.entities.Tables;
import com.example.adriatik.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {


    List<Reservation> findAll();
    boolean existsByTableAndReservationTimeAndReservationDate(Tables table, ReservationTime reservationTime, LocalDate date);


    List<Reservation> findAllByOrderByIdAsc();

    List<Reservation> findAllByUser(User user);

    boolean existsById(Integer id);

    List<Reservation> findByTableAndReservationDate(Tables table, LocalDate date);

    List<Reservation> findByReservationDateAndReservationTime( LocalDate reservationDate, ReservationTime reservationTime);
    }