package com.satyam.doctorbooking.dto;

public class DoctorSignupRequest {
    private String name;
    private String email;
    private String password;
    private String specialization;
    private String hospitalName;
    private int fees;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name){
        this.name=name;
    }
    public String getName() {
        return this.name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public int getFees() {
        return fees;
    }
}
