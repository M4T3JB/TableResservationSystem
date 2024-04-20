package com.example.adriatik.controllers;

import com.example.adriatik.entities.User;
import com.example.adriatik.dto.UserPayload;
import com.example.adriatik.entities.Role;
import com.example.adriatik.repositories.UserRepository;
import com.example.adriatik.services.UserService;
import com.example.adriatik.repositories.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    public UserController(UserService userService, UserRepository userRepository, RoleRepository roleRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/register")
    public String addUserForm(Model model) {
        try {
            var roles = roleRepository.findAll();
            roles.removeFirst();
            model.addAttribute("roleOptions", roles);
            model.addAttribute("userPayload", new UserPayload());
            return "user/addUserForm";
        } catch (SecurityException e) {
            return "error/unauthorized";
        } catch (Exception e) {

            return "error/internalServerError";
        }
    }

    @PostMapping("/addUser")
    public String addUser(@ModelAttribute("userPayload") UserPayload userPayload, RedirectAttributes redirectAttributes) {
        try {
            if (userService.existsByUsername(userPayload.getUsername())) {
                redirectAttributes.addFlashAttribute("error", "User with the same username already exists");
                return "redirect:/user/register";
            }
            else if (userService.existsByEmail(userPayload.getEmail())) {
                redirectAttributes.addFlashAttribute("error", "User with the same email already exists");
                return "redirect:/user/register";
            }

            var user = userService.addUser(userPayload);
            userRepository.save(user);
            return "redirect:/user/registrationSuccess";
        } catch (SecurityException e) {
            redirectAttributes.addFlashAttribute("error", "Unauthorized: " + e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + userPayload.getUsername() + e.getMessage() + userPayload.getUserRole());
        }
        return "redirect:/error";
    }



    @GetMapping("/registrationSuccess")
    public String registrationSuccess() {
        return "user/registrationSuccess";
    }

    @GetMapping("/login")
    public String login()
    {
        return "user/login";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT', 'EMPLOYEE')")
    @GetMapping("/{id}/editForm")
    public String editUserForm(@PathVariable("id") Integer id, @ModelAttribute("userPayload") UserPayload userPayload, @ModelAttribute("rolePayload") Role rolePayload, Model model) {
        try {
            User user = userService.findById(id);

            var roles = roleRepository.findAll();
            roles.remove(0);
            model.addAttribute("roleOptions", roles);

            userPayload.setUsername(user.getUsername());
            userPayload.setFirstname(user.getFirstname());
            userPayload.setEmail(user.getEmail());
            userPayload.setLastname(user.getLastname());
            userPayload.setDateOfBirth(user.getDateOfBirth());
            userPayload.setUserRole(user.getUserRole().getName());
            userPayload.setPassword(user.getPassword());
            userPayload.setEnabled(true);

            model.addAttribute("userPayload", userPayload);

            return "user/editUserForm";
        } catch (EntityNotFoundException e) {
            return "error/userNotFound";
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT', 'EMPLOYEE')")
    @PostMapping("/{id}/edit")
    public String editUser(@PathVariable("id") Integer id, @ModelAttribute("userPayload") UserPayload userPayload,
                           RedirectAttributes redirectAttributes) {
        try {
            userService.editUserById(id, userPayload);
            redirectAttributes.addFlashAttribute("message", "User with ID=" + id + " has been updated successfully!");
            if ("CLIENT".equals(userPayload.getUserRole()))
                return "redirect:/admin/showAllClients";
            else
                return "redirect:/admin/showAllEmployees";
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/showAllClients";
        }
    }
}


