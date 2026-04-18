package com.satyam.doctorbooking.controller;

import com.satyam.doctorbooking.model.Doctor;
import com.satyam.doctorbooking.service.AppointmentService;
import com.satyam.doctorbooking.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;
    private AppointmentService appointmentService;
    // Get all doctors
    @GetMapping
    public List<Doctor> getDoctors(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String specialization) {

        return doctorService.searchDoctors(name, specialization);
    }

    // Recommended doctors
    @GetMapping("/recommended")
    public List<Doctor> getRecommendedDoctors() {
        return doctorService.getRecommendedDoctors();
    }
    @GetMapping("/{doctorId}/slots")
    public List<String> getAvailableSlots(
            @PathVariable Long doctorId,
            @RequestParam String date) {

        return appointmentService.getAvailableSlots(
                doctorId,
                LocalDate.parse(date)
        );
    }
}