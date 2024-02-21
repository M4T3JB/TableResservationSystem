package com.example.adriatik.repositories;

import com.example.adriatik.entities.Program;
import com.example.adriatik.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramRepository extends JpaRepository<Program, Integer> {
    Program findByEmployeeId(Integer employeeId);

    Program findByClientAndEmployee(User client, User employee);

    Program findFirstByClientAndEmployeeOrderByIdAsc(User client, User employee);

    Program findByClientAndEmployeeOrderByIdDesc(User client, User employee);

    Program findFirstByClientAndEmployeeOrderByIdDesc(User client, User employee);
}
