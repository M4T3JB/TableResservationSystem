package com.example.adriatik.services;

import com.example.adriatik.entities.User;
import com.example.adriatik.repositories.RoleRepository;
import com.example.adriatik.repositories.UserRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Service
public class ClientServiceImpl implements ClientService{

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Autowired
    public ClientServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public List<User> getEmployees() {
        return userRepository.findByUserRole_name("EMPLOYEE");
    }

    @Override
    public List<User> getEmployeesAsc() {
        return userRepository.findByUserRole_nameOrderByFirstnameAsc("EMPLOYEE");
    }


    @Override
    public List<User> getEmployeesDesc() {
        return userRepository.findByUserRole_nameOrderByFirstnameDesc("EMPLOYEE");
    }
}
