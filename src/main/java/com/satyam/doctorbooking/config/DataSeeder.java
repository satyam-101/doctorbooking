package com.satyam.doctorbooking.config;

import com.satyam.doctorbooking.model.Doctor;
import com.satyam.doctorbooking.model.Role;
import com.satyam.doctorbooking.model.User;
import com.satyam.doctorbooking.repository.DoctorRepository;
import com.satyam.doctorbooking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public void run(String... args) {
        seedUsers();
        seedDoctors();
    }

    private void seedUsers() {
        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setName("Admin");
            admin.setEmail("admin@doctor.com");
            admin.setPassword(encoder.encode("admin123"));
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);

            User user = new User();
            user.setName("Test User");
            user.setEmail("user@test.com");
            user.setPassword(encoder.encode("user123"));
            user.setRole(Role.USER);
            userRepository.save(user);
        }
    }

    private void seedDoctors() {
        if (doctorRepository.count() == 0) {
            saveDoctor("Dr. Rahul Sharma", "Cardiologist", 500, 4.8, "City Hospital");
            saveDoctor("Dr. Priya Patel", "Dermatologist", 400, 4.6, "Skin Care Center");
            saveDoctor("Dr. Amit Kumar", "Orthopedic", 600, 4.9, "Bone & Joint Clinic");
            saveDoctor("Dr. Sneha Gupta", "Pediatrician", 450, 4.7, "Child Care Hospital");
            saveDoctor("Dr. Vikram Singh", "Neurologist", 700, 4.5, "Neuro Care Center");
            saveDoctor("Dr. Neha Agarwal", "Gynecologist", 550, 4.8, "Women's Hospital");
            saveDoctor("Dr. Rajesh Verma", "General Physician", 300, 4.3, "Health Clinic");
            saveDoctor("Dr. Ananya Das", "Ophthalmologist", 480, 4.6, "Eye Care Center");
        }
    }

    private void saveDoctor(String name, String specialization, int fees, double rating, String hospitalName) {
        Doctor doctor = new Doctor();
        doctor.setName(name);
        doctor.setSpecialization(specialization);
        doctor.setFees(fees);
        doctor.setRating(rating);
        doctor.setHospitalName(hospitalName);
        doctorRepository.save(doctor);
    }
}
