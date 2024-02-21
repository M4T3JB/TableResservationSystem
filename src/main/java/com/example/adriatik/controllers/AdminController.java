package com.example.adriatik.controllers;


import com.example.adriatik.entities.User;
import com.example.adriatik.repositories.RoleRepository;
import com.example.adriatik.repositories.UserRepository;
import com.example.adriatik.services.AdminService;
import com.example.adriatik.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;
    private final UserService userService;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    public AdminController(AdminService adminService, UserRepository userRepository, RoleRepository roleRepository, UserService userService) {
        this.adminService = adminService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @GetMapping()
    public String AdminIndex(){
            return "admin/adminIndex";
    }

    @GetMapping("/showAllEmployees")
    public String showAllEmployees(Model model){
        List<User> employees = adminService.getEmployees();
        model.addAttribute("employees", employees);

        return "admin/showAllEmployees";
    }

    @GetMapping("/showAllClients")
    public String showAllClients(Model model){
        List<User> clients = adminService.getClients();
        model.addAttribute("clients", clients);

        return "admin/showAllClients";
    }

    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            User user = adminService.getUserById(id);
            String roleName = user.getUserRole().getName(); // Get the role name

            adminService.deleteUserById(id);

            if (roleName.equals("CLIENT")) {
                redirectAttributes.addFlashAttribute("message", "The user with id=" + id + " has been deleted successfully!");
                return "redirect:/admin/showAllClients";
            } else {
                redirectAttributes.addFlashAttribute("message", "The user with id=" + id + " has been deleted successfully!");
                return "redirect:/admin/showAllEmployees";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/admin/showAllClients";
        }
    }
}
