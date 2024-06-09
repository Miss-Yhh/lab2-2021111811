package com.example.model;

import lombok.Data;

import java.sql.Date;


@Data
public class AvailabilitySlot {

    private int slotId;
    private int teacherId;
    private Date startTime;
    private Date endTime;
    private String status;
    private Date createdAt;
    private Date updatedAt;
}