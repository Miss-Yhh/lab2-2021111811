package com.example.model;

import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;
@Data
public class Teacher {
    private int teacherId;
    private String teacherName;
    private String subject;
    private String email;
    private String phoneNumber;
    private String address;
    private String password;



    // Getters
    public int getTeacherId() {
        return teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public String getSubject() {
        return subject;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }

    // Setters
    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
