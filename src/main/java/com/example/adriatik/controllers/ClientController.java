package com.example.adriatik.controllers;



import com.example.adriatik.dto.ReservationPayload;
import com.example.adriatik.repositories.ReservationTimeRepository;
import com.example.adriatik.repositories.TablesRepository;
import com.example.adriatik.services.ReservationService;
import com.example.adriatik.entities.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import java.security.Principal;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/client")
public class ClientController {


    private final ReservationService reservationService;

    @Autowired
    private TablesRepository tablesRepository;


    @Autowired
    private ReservationTimeRepository reservationTimeRepository;

    public ClientController(ReservationService reservationService) {

        this.reservationService = reservationService;
    }

    @GetMapping()
    public String viewHomePage() {
        return "client/clientIndex";
    }


    @GetMapping("reservationSuccess")
    public String reservationSuccess() {
        return "reservation/reservationSuccess";
    }


    @GetMapping("addReservation")
    public String showAddReservationForm(Model model) {
        List<ReservationTime> reservationTimes = reservationTimeRepository.findAll();
        List<Tables> tables = tablesRepository.findAll();
        model.addAttribute("reservationTimes", reservationTimes);
        model.addAttribute("tables", tables);
        return "reservation/addReservationForm";
    }

    @PostMapping("/saveReservation")
    public String saveReservation(@ModelAttribute ReservationPayload reservationPayload, RedirectAttributes redirectAttributes) throws ParseException {
        Reservation reservation = reservationService.addReservation(reservationPayload);
        if (reservation != null) {
            redirectAttributes.addFlashAttribute("success", "Reservation saved successfully!");
            return "redirect:/client/reservationSuccess";
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to save reservation. Please try again.");
            return "redirect:/client/addReservation";
        }
    }


    @PostMapping("/deleteReservation/{id}")
    public String deleteReservation(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        boolean isDeleted = reservationService.deleteReservationById(id);
        if (isDeleted) {
            redirectAttributes.addFlashAttribute("deleteSuccess", true);
        }
        return "redirect:/client/viewReservations";
    }

    @GetMapping("/viewReservations")
    public String viewMyReservations(Model model, Principal principal) {
        String username = principal.getName(); // Get the username of the currently logged-in client
        List<Reservation> reservations = reservationService.findAllReservationsByUser(username);
        model.addAttribute("reservations", reservations);
        return "reservation/viewReservation";
    }

    @GetMapping("/getReservedTimes")
    @ResponseBody
    public List<String> getReservedTimes(@RequestParam("tableId") Integer tableId, @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        // Query the database for reservations for the selected table and date
        List<Reservation> reservations = reservationService.findReservationsByTableAndDate(tableId, date);

        // Extract the times from the reservations and return them
        return reservations.stream()
                .map(reservation -> reservation.getReservationTime().getTime())
                .collect(Collectors.toList());
    }


}

