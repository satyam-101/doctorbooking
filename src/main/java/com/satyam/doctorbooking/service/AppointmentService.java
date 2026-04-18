package com.satyam.doctorbooking.service;

import com.satyam.doctorbooking.dto.AppointmentResponse;
import com.satyam.doctorbooking.model.*;
import com.satyam.doctorbooking.repository.AppointmentRepository;
import com.satyam.doctorbooking.repository.DoctorRepository;
import com.satyam.doctorbooking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private UserRepository userRepository;

    public Appointment bookAppointment(Long doctorId, String email, String patientName, String slot, LocalDate date) {

        if (date.isBefore(LocalDate.now())) {
            throw new RuntimeException("Cannot book appointment for past dates");
        }

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        boolean exists = appointmentRepository
                .existsByDoctorAndDateAndSlot(doctor, date, slot);

        if (exists) {
            throw new RuntimeException("Slot already booked");
        }

        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setUser(user);
        appointment.setPatientName(patientName);
        appointment.setSlot(slot);
        appointment.setDate(date);
        appointment.setStatus(AppointmentStatus.CONFIRMED);

        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getUserAppointments(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        return appointmentRepository.findByUser(user);
    }
    private static final List<String> ALL_SLOTS = List.of(
            "09:00 AM",
            "10:00 AM",
            "11:00 AM",
            "12:00 PM",
            "02:00 PM",
            "03:00 PM",
            "04:00 PM"
    );

    public void cancelAppointment(Long id, String email) {
        Appointment appt = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (!appt.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Unauthorized");
        }

        appt.setStatus(AppointmentStatus.CANCELLED);
        appointmentRepository.save(appt);
    }
    public List<AppointmentResponse> getDoctorAppointments(String email) {

        Doctor doctor = doctorRepository.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        return appointmentRepository.findByDoctor(doctor)
                .stream()
                .map(appt -> {
                    AppointmentResponse res = new AppointmentResponse();
                    res.setId(appt.getId());
                    res.setPatientName(appt.getPatientName());
                    res.setSlot(appt.getSlot());
                    res.setDate(appt.getDate());
                    res.setStatus(appt.getStatus().name());
                    res.setDoctorName(appt.getDoctor().getName());
                    return res;
                })
                .toList();
    }
    public Appointment acceptAppointment(Long id, String email) {

        Appointment appt = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (!appt.getDoctor().getUser().getEmail().equals(email)) {
            throw new RuntimeException("Unauthorized");
        }

        appt.setStatus(AppointmentStatus.CONFIRMED);
        return appointmentRepository.save(appt);
    }
    public Appointment rejectAppointment(Long id, String email) {

        Appointment appt = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (!appt.getDoctor().getUser().getEmail().equals(email)) {
            throw new RuntimeException("Unauthorized");
        }

        appt.setStatus(AppointmentStatus.REJECTED);
        return appointmentRepository.save(appt);
    }
    public Appointment completeAppointment(Long id, String email) {

        Appointment appt = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (!appt.getDoctor().getUser().getEmail().equals(email)) {
            throw new RuntimeException("Unauthorized");
        }

        appt.setStatus(AppointmentStatus.COMPLETED);
        return appointmentRepository.save(appt);
    }
    public Appointment createAppointmentByDoctor(
            String doctorEmail,
            String patientName,
            String email,
            String slot,
            LocalDate date) {

        if (date.isBefore(LocalDate.now())) {
            throw new RuntimeException("Cannot book past dates");
        }

        Doctor doctor = doctorRepository.findByUserEmail(doctorEmail)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        // 🔍 Check if patient exists
        User user = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setName(patientName);
                    newUser.setEmail(email);
                    newUser.setPassword("default123"); // or random password
                    newUser.setRole(Role.USER);
                    return userRepository.save(newUser);
                });

        // ❗ Slot check
        boolean exists = appointmentRepository
                .existsByDoctorAndDateAndSlot(doctor, date, slot);

        if (exists) {
            throw new RuntimeException("Slot already booked");
        }

        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setUser(user);
        appointment.setPatientName(patientName);
        appointment.setSlot(slot);
        appointment.setDate(date);
        appointment.setStatus(AppointmentStatus.CONFIRMED);

        return appointmentRepository.save(appointment);
    }
    public List<String> getAvailableSlots(Long doctorId, LocalDate date) {

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        // Get booked slots
        List<Appointment> appointments =
                appointmentRepository.findByDoctorAndDate(doctor, date);

        List<String> bookedSlots = appointments.stream()
                .filter(a -> a.getStatus() != AppointmentStatus.CANCELLED &&
                        a.getStatus() != AppointmentStatus.REJECTED)
                .map(Appointment::getSlot)
                .toList();

        // Filter available
        return ALL_SLOTS.stream()
                .filter(slot -> !bookedSlots.contains(slot))
                .toList();
    }
}