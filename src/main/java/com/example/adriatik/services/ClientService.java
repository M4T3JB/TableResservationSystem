package com.example.adriatik.services;

import com.example.adriatik.entities.User;

import java.util.List;

public interface ClientService {
    List<User> getEmployees();

    List<User> getEmployeesAsc();

    List<User> getEmployeesDesc();

}
