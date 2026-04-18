package com.satyam.doctorbooking.model;

import java.time.LocalDate;
import lombok.Data;
@Data
public class DoctorAppointmentRequest {
    private String patientName;
    private String email;   // NEW
    private String slot;
    private LocalDate date;
}


