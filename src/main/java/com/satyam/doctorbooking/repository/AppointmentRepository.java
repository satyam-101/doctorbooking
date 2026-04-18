package com.satyam.doctorbooking.repository;

import com.satyam.doctorbooking.model.Appointment;
import com.satyam.doctorbooking.model.Doctor;
import com.satyam.doctorbooking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // Get all appointments of a user
    List<Appointment> findByUser(User user);
    List<Appointment> findByDoctor(Doctor doctor);
    List<Appointment> findByDoctorAndDate(Doctor doctor, LocalDate date);

    // Check if slot already booked
    boolean existsByDoctorAndDateAndSlot(Doctor doctor, LocalDate date, String slot);


}