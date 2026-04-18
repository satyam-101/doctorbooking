package com.satyam.doctorbooking.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String specialization;
    private int fees;
    private double rating;
    private String hospitalName;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public int getFees() {
        return fees;
    }

    public double getRating() {
        return rating;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public User getUser() {
        return user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void setFees(int fees) {
        this.fees = fees;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public void setUser(User user) {
        this.user = user;
    }
}