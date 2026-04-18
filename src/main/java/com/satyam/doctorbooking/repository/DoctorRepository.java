package com.satyam.doctorbooking.repository;

import com.satyam.doctorbooking.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    List<Doctor> findBySpecialization(String specialization);
    List<Doctor> findByNameContainingIgnoreCase(String name);
    List<Doctor> findByNameContainingIgnoreCaseAndSpecializationIgnoreCase(
            String name, String specialization
    );
    Optional<Doctor> findByUserEmail(String email);
}