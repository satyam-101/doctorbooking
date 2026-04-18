package com.satyam.doctorbooking.controller;

import com.satyam.doctorbooking.model.Doctor;
import com.satyam.doctorbooking.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private DoctorRepository doctorRepository;

    // Add doctor
    @PostMapping("/doctor")
    public Doctor addDoctor(@RequestBody Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    // Delete doctor
    @DeleteMapping("/doctor/{id}")
    public String deleteDoctor(@PathVariable Long id) {
        doctorRepository.deleteById(id);
        return "Doctor deleted";
    }
}

