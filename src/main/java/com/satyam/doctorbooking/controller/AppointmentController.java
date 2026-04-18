package com.satyam.doctorbooking.controller;

import com.satyam.doctorbooking.dto.AppointmentRequest;
import com.satyam.doctorbooking.model.Appointment;
import com.satyam.doctorbooking.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    // Book appointment
    @PostMapping
    public Appointment bookAppointment(
            @Valid @RequestBody AppointmentRequest request,
            Authentication auth) {

        return appointmentService.bookAppointment(
                request.getDoctorId(),
                auth.getName(),
                request.getPatientName(),
                request.getSlot(),
                request.getDate()
        );
    }

    // Get user appointments
    @GetMapping("/my")
    public List<Appointment> getMyAppointments(Authentication auth) {
        return appointmentService.getUserAppointments(auth.getName());
    }

    // Cancel appointment
    @DeleteMapping("/{id}")
    public String cancel(@PathVariable Long id, Authentication auth) {
        appointmentService.cancelAppointment(id, auth.getName());
        return "Appointment cancelled";
    }
}