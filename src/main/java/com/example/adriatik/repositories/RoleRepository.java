package com.example.adriatik.repositories;

import com.example.adriatik.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByName(String name);

    List<Role> findAll();

}
