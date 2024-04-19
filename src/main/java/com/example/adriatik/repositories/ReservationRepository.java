
package com.example.adriatik.repositories;


import com.example.adriatik.entities.Reservation;
import com.example.adriatik.entities.ReservationTime;
import com.example.adriatik.entities.Tables;
import com.example.adriatik.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.time.LocalTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {


    List<Reservation> findAll();
    boolean existsByTableAndReservationTime(Tables table, ReservationTime reservationTime);


    List<Reservation> findAllByOrderByIdAsc();

    List<Reservation> findAllByUser(User user);

    boolean existsById(Integer id);

}