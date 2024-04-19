package com.example.adriatik.repositories;

import com.example.adriatik.entities.ReservationTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationTimeRepository extends JpaRepository<ReservationTime, Integer> {
    List<ReservationTime> findAll();
}