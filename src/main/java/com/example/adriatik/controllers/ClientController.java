package com.example.adriatik.controllers;



import com.example.adriatik.dto.ReservationPayload;
import com.example.adriatik.services.ReservationService;
import com.example.adriatik.entities.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/client")
public class ClientController {


    private final ReservationService reservationService;



    public ClientController(ReservationService reservationService) {

        this.reservationService = reservationService;
    }

    @GetMapping()
    public String viewHomePage() {
        return "client/clientIndex";
    }




    @GetMapping("addReservation")
    public String showAddReservationForm(Model model) {
        model.addAttribute("reservation", new Reservation());
        return "reservation/addReservationForm";
    }

    @GetMapping("/{id}/editReservationForm")
    public String editReservationForm(@PathVariable("id") Integer id, Model model) {
        Reservation reservation = reservationService.findById(id);
        model.addAttribute("reservation", reservation);
        return "client/editReservationForm";
    }

    @PostMapping("/editReservation/{id}")
    public String editReservation(@PathVariable("id") Integer id, @ModelAttribute ReservationPayload reservationPayload, RedirectAttributes redirectAttributes) {
        reservationService.editReservation(id, reservationPayload);
        redirectAttributes.addFlashAttribute("success", "Reservation updated successfully!");
        return "redirect:/employee/showAllReservations";
    }

    @PostMapping("/deleteReservation/{id}")
    public String deleteReservation(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        boolean isDeleted = reservationService.deleteReservationById(id);
        if (isDeleted) {
            redirectAttributes.addFlashAttribute("deleteSuccess", true);
        }
        return "redirect:/employee/showAllReservations";
    }
}
