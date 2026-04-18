package com.satyam.doctorbooking.controller;

import com.satyam.doctorbooking.dto.AppointmentResponse;
import com.satyam.doctorbooking.model.Appointment;
import com.satyam.doctorbooking.model.DoctorAppointmentRequest;
import com.satyam.doctorbooking.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctor")
public class DoctorDashboardController  {
    @Autowired
    private AppointmentService appointmentService;

    // 1. View appointments
    @GetMapping("/appointments")
    public List<AppointmentResponse> getAppointments(Authentication auth) {
        return appointmentService.getDoctorAppointments(auth.getName());
    }

    // 2. Accept
    @PutMapping("/appointments/{id}/accept")
    public Appointment accept(@PathVariable Long id, Authentication auth) {
        return appointmentService.acceptAppointment(id, auth.getName());
    }

    // 3. Reject
    @PutMapping("/appointments/{id}/reject")
    public Appointment reject(@PathVariable Long id, Authentication auth) {
        return appointmentService.rejectAppointment(id, auth.getName());
    }

    // 4. Complete
    @PutMapping("/appointments/{id}/complete")
    public Appointment complete(@PathVariable Long id, Authentication auth) {
        return appointmentService.completeAppointment(id, auth.getName());
    }
    @PostMapping("/appointments")
    public Appointment createAppointment(
            @RequestBody DoctorAppointmentRequest request,
            Authentication auth) {

        return appointmentService.createAppointmentByDoctor(
                auth.getName(),
                request.getPatientName(),
                request.getEmail(),
                request.getSlot(),
                request.getDate()
        );
    }
}

