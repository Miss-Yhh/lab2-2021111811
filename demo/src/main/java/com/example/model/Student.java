package com.example.model;

import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;
@Data
public class Student {
    private int studentId;
    private String studentName;
    private String email;
    private String phoneNumber;
    private String studentMajor;
    private String password;

    public Student() {
    }

    // Getters
    public int getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getStudentMajor() {
        return studentMajor;
    }

    public String getPassword() {
        return password;
    }

    // Setters
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setStudentMajor(String studentMajor) {
        this.studentMajor = studentMajor;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

