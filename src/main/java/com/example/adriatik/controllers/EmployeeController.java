package com.example.adriatik.controllers;

import com.example.adriatik.dto.ReservationPayload;

import com.example.adriatik.entities.*;

import com.example.adriatik.repositories.UserRepository;
import com.example.adriatik.services.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.text.ParseException;
import java.util.List;


@Controller
@RequestMapping("/employee")
public class EmployeeController {
    private final ReservationService reservationService;








    @Autowired
    public EmployeeController(ReservationService reservationService

    ) {
        this.reservationService = reservationService;




    }

    @PreAuthorize("hasAnyAuthority('EMPLOYEE')")
    @GetMapping()
    public String viewHomePage() {
        return "employee/employeeIndex";
    }

    @PreAuthorize("hasAnyAuthority('EMPLOYEE')")
    @GetMapping("reservationSuccess")
    public String reservationSuccess() {
        return "reservation/reservationSuccess";
    }

    @PreAuthorize("hasAnyAuthority('EMPLOYEE')")
    @GetMapping("addReservation")
    public String showAddReservationForm(Model model) {
        model.addAttribute("reservation", new Reservation());
        return "reservation/addReservationForm";
    }
    @PreAuthorize("hasAnyAuthority('EMPLOYEE')")
    @PostMapping("/saveReservation")
    public String saveReservation(@ModelAttribute ReservationPayload reservationPayload, RedirectAttributes redirectAttributes) throws ParseException {
        Reservation reservation = reservationService.addReservation(reservationPayload);
        if (reservation != null) {
            redirectAttributes.addFlashAttribute("success", "Reservation saved successfully!");
            return "redirect:/employee/reservationSuccess";
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to save reservation. Please try again.");
            return "redirect:/employee/addReservation";
        }
    }


    @PreAuthorize("hasAnyAuthority('EMPLOYEE')")
    @GetMapping("showAllReservations")
    public String showAllReservations(Model model) {
        List<Reservation> reservations = reservationService.findAllReservationsOrderedByIdAsc();
        model.addAttribute("reservations", reservations);
        return "reservation/showAllReservations";
    }

    @PreAuthorize("hasAnyAuthority('EMPLOYEE')")
    @PostMapping("/deleteReservation/{id}")
    public String deleteReservation(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        boolean isDeleted = reservationService.deleteReservationById(id);
        if (isDeleted) {
            redirectAttributes.addFlashAttribute("deleteSuccess", true);
        }
        return "redirect:/employee/showAllReservations";
    }

    @GetMapping("/editReservationForm/{id}")
    public String editReservationForm(@PathVariable("id") Integer id, @ModelAttribute("reservationPayload") ReservationPayload reservationPayload, Model model) {
        try {
            Reservation reservation = reservationService.findById(id);
            reservationPayload.setId(reservation.getId());
            reservationPayload.setUserId(reservation.getUser().getId());
            reservationPayload.setTableId(reservation.getTable().getId());
            reservationPayload.setReservationDate(reservation.getReservationDate());
            reservationPayload.setReservationTime(reservation.getReservationTime());
            // Add other reservation fields here

            model.addAttribute("reservationPayload", reservationPayload);

            return "reservation/editReservation";
        } catch (EntityNotFoundException e) {
            return "error/reservationNotFound";
        }
    }

    @PreAuthorize("hasAnyAuthority('EMPLOYEE')")
    @PostMapping("/editReservation/{id}")
    public String editReservation(@PathVariable("id") Integer id, @ModelAttribute ReservationPayload reservationPayload, RedirectAttributes redirectAttributes) {
        reservationService.editReservation(id, reservationPayload);
        redirectAttributes.addFlashAttribute("success", "Reservation updated successfully!");
        return "redirect:/employee/showAllReservations";
    }



}
