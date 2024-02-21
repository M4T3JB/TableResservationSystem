package com.example.adriatik.services;

import com.example.adriatik.entities.User;
import com.example.adriatik.dto.UserPayload;

import java.text.ParseException;

public interface UserService {
    User addUser(UserPayload userPayload) throws ParseException;
    User findById(Integer id);
    void editUserById(Integer id, UserPayload userPayload);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

}
