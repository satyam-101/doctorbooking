package com.satyam.doctorbooking.dto;
import lombok.Data;
import java.time.LocalDate;
@Data
public class AppointmentResponse {
    private Long id;
    private String patientName;
    private String slot;
    private LocalDate date;
    private String status;
    private String doctorName;
}
