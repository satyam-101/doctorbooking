package com.satyam.doctorbooking.controller;

import com.satyam.doctorbooking.dto.DoctorSignupRequest;
import com.satyam.doctorbooking.dto.LoginRequest;
import com.satyam.doctorbooking.model.Doctor;
import com.satyam.doctorbooking.model.Role;
import com.satyam.doctorbooking.model.User;
import com.satyam.doctorbooking.repository.DoctorRepository;
import com.satyam.doctorbooking.repository.UserRepository;
import com.satyam.doctorbooking.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @PostMapping("/signup")
    public String signup(@Valid @RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
        return "User registered";
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginRequest request) {
        User existing = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(request.getPassword(), existing.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtUtil.generateToken(existing.getEmail(),existing.getRole());
    }
    @Autowired
    private DoctorRepository doctorRepository;
    @PostMapping("/doctor/signup")
    public String doctorSignup(@RequestBody DoctorSignupRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        // Create User
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setRole(Role.DOCTOR);

        userRepository.save(user);

        // Create Doctor
        Doctor doctor = new Doctor();
        doctor.setName(request.getName());
        doctor.setSpecialization(request.getSpecialization());
        doctor.setHospitalName(request.getHospitalName());
        doctor.setFees(request.getFees());
        doctor.setRating(0.0);
        doctor.setUser(user);

        doctorRepository.save(doctor);

        return "Doctor registered successfully";
    }
}