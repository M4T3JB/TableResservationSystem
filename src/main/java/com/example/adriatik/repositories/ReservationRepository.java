
package com.example.adriatik.repositories;


import com.example.adriatik.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    Reservation findByTableNumber(Integer tableNumber);

    List<Reservation> findAll();

    boolean existsByTableNumber(Integer tableNumber);

    List<Reservation> findAllByOrderByIdAsc();

}