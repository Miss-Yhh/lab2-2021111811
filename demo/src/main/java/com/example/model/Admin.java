package com.example.model;

import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;
@Data
public class Admin {
    private int adminId;
    private String adminName;
    private String email;
    private String password;

    public Admin() {
    }
    // Getters
    public int getAdminId() {//获取管理员id
        return adminId;
    }

    public String getAdminName() {//获取管理员name
        return adminName;
    }

    public String getEmail() {//获取管理员email
        return email;
    }

    public String getPassword() {//获取管理员密码
        return password;
    }

    // Setters
    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
