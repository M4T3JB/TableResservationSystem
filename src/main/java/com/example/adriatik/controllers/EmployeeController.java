package com.example.adriatik.controllers;

import com.example.adriatik.dto.ReservationPayload;
import com.example.adriatik.entities.*;
import com.example.adriatik.repositories.ProgramRepository;
import com.example.adriatik.repositories.UserRepository;
import com.example.adriatik.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;


@Controller
@RequestMapping("/employee")
public class EmployeeController {
    private final ReservationService reservationService;

    private final UserRepository userRepository;

    private final SetReservationService setReservationService;
    private final ProgramRepository programRepository;


    @Autowired
    public EmployeeController(ReservationService reservationService, ClientRequestService clientRequestService,
                              UserRepository userRepository, ProgramService programService,
                              SetReservationService setReservationService, ProgramRepository programRepository
    ) {
        this.reservationService = reservationService;

        this.userRepository = userRepository;

        this.setReservationService = setReservationService;
        this.programRepository = programRepository;

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
    @PostMapping("saveReservation")
    public String saveReservation(@ModelAttribute ReservationPayload reservationPayload, RedirectAttributes redirectAttributes) throws ParseException {
        boolean reservationExists = reservationService.existsByNumber(reservationPayload.getTableNumber());
        if (reservationExists) {
            redirectAttributes.addFlashAttribute("error", "Reservation with the same name already exists");
            return "redirect:/employee/addReservation";
        }

        reservationService.addReservation(reservationPayload);
        return "redirect:/employee/reservationSuccess";
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
    public String editReservationForm(@PathVariable Integer id, Model model) {
        Reservation reservation = reservationService.findById(id);
        model.addAttribute("reservation", reservation);
        return "reservation/editReservation";
    }

    @PreAuthorize("hasAnyAuthority('EMPLOYEE')")
    @PostMapping("/editReservation/{id}")
    public String editReservation(@PathVariable("id") Integer id, @ModelAttribute ReservationPayload reservationPayload, RedirectAttributes redirectAttributes) {
        reservationService.editReservation(id, reservationPayload);
        redirectAttributes.addFlashAttribute("success", "Reservation updated successfully!");
        return "redirect:/employee/showAllReservations";
    }


    @PreAuthorize("hasAnyAuthority('EMPLOYEE')")
    @PostMapping("/addSetReservation")
    public String addSetReservation(@RequestParam Integer selectedReservationId, @RequestParam LocalDateTime reservationTime, @RequestParam Integer reservationCount, @RequestParam Integer userId, @RequestParam Integer programId) {
        SetReservation setReservation = new SetReservation();
        setReservation.setId(selectedReservationId);
        setReservation.setTableNumber(reservationCount);
        User user = userRepository.findById(userId).orElse(null);
        setReservation.setUser(user);
        Program program = programRepository.findById(programId).orElse(null);
        setReservation.setReservationTime(reservationTime);
        setReservation.setProgram(program);
        setReservationService.addSetReservation(setReservation);
        return "redirect:/employee/writeProgram/" + program.getClient().getId() + "/" + program.getEmployee().getId();
    }
}
