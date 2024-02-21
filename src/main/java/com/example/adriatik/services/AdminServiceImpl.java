package com.example.adriatik.services;

import com.example.adriatik.entities.User;
import com.example.adriatik.repositories.RoleRepository;
import com.example.adriatik.repositories.UserRepository;
import javassist.NotFoundException;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Service
public class AdminServiceImpl implements AdminService{
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Autowired
    public AdminServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public User getUserById(Integer id) throws NotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }

    public List<User> getClients() {
        return userRepository.findByUserRole_name("CLIENT");
    }
    public List<User> getEmployees() {
        return userRepository.findByUserRole_name("EMPLOYEE");
    }

    @Override
    public void deleteUserById(Integer id) {
        userRepository.findById(id).ifPresent(userRepository::delete);
    }
}
