package com.example.adriatik.repositories;

import com.example.adriatik.entities.ClientRequest;
import com.example.adriatik.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRequestRepository extends JpaRepository<ClientRequest, Integer> {
    List<ClientRequest> findByEmployee(User employee);
}
