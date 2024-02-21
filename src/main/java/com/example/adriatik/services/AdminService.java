package com.example.adriatik.services;

import com.example.adriatik.entities.User;
import javassist.NotFoundException;

import java.util.List;

public interface AdminService {
    List<User> getClients();
    List<User> getEmployees();

    public User getUserById(Integer id) throws NotFoundException;

    void deleteUserById(Integer id);


}
