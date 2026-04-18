package com.satyam.doctorbooking.service;

import com.satyam.doctorbooking.model.Doctor;
import com.satyam.doctorbooking.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    // Get all doctors
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }
    // Recommended doctors
    public List<Doctor> getRecommendedDoctors() {
        return doctorRepository.findAll()
                .stream()
                .filter(d -> d.getRating() > 4.5)
                .toList();
    }
    public List<Doctor> searchDoctors(String name, String specialization) {
        if (name != null && specialization != null) {
            return doctorRepository
                    .findByNameContainingIgnoreCaseAndSpecializationIgnoreCase(name, specialization);
        } else if (name != null) {
            return doctorRepository.findByNameContainingIgnoreCase(name);
        } else if (specialization != null) {
            return doctorRepository.findBySpecialization(specialization);
        } else {
            return doctorRepository.findAll();
        }
    }
}