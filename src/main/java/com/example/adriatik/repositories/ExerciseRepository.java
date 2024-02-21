package com.example.adriatik.repositories;

import com.example.adriatik.entities.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {

    Exercise findByName(String name);

    List<Exercise> findAll();

    boolean existsByName(String name);

    List<Exercise> findAllByOrderByIdAsc();


}
