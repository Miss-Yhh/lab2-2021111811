package com.example.controller;

import com.example.service.AdminService;
import com.example.service.StudentService;
import com.example.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public HttpStatus login(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                            @RequestParam("role") String role) {
        if ("student".equals(role)) {
            return studentService.login(username, password);
        } else if ("teacher".equals(role)) {
            return teacherService.login(username, password);
        } else if ("admin".equals(role)) {
            return adminService.login(username, password);
        } else {
            return HttpStatus.UNAUTHORIZED;
        }
    }


}
