package com.example.model;

import lombok.Data;

import java.security.Timestamp;
import java.sql.Date;
@Data
public class Appointment {
    private int appointmentId;
    private int teacherId;
    private int studentId;
    private int slotId;
    private String status;
    private Date createdAt;
    private Date updatedAt;
    public void setId(int id) {
        this.appointmentId = id;
    }
}

