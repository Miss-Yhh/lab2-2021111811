package com.example.controller;

import com.example.model.Teacher;
import com.example.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private TeacherService teacherService;

    @PostMapping("/teacher/add")
    public ResponseEntity<String> addTeacher(@RequestBody Teacher teacher) {
        if (teacher == null) {
            return new ResponseEntity<>("教师信息不能为空", HttpStatus.BAD_REQUEST);
        }
        // 检查必要的属性是否缺失
        if (teacher.getTeacherName() == null || teacher.getTeacherName().isEmpty() ||
                teacher.getSubject() == null || teacher.getSubject().isEmpty() ||
                teacher.getEmail() == null || teacher.getEmail().isEmpty()) {
            return new ResponseEntity<>("Teacher name, subject, and email address are mandatory fields", HttpStatus.BAD_REQUEST);
        }
        try {
            teacherService.addTeacher(teacher);
            return new ResponseEntity<>("teacher added successfully", HttpStatus.OK);
        } catch (Exception e) {
            // 捕获可能的异常，如数据库约束违规等
            if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
                return new ResponseEntity<>("Email is already taken on", HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>("some error ,please try later", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @GetMapping("/teacher/delete/{teacherId}")
    public ResponseEntity<String> deleteTeacher(@PathVariable int teacherId) {
        if(teacherService.deleteTeacher(teacherId) > 0)
        {
            return new ResponseEntity<>("teacher deleted successfully", HttpStatus.OK); // 假设有一个页面或列表来展示教师信息
        }
        else{
            return new ResponseEntity<>("teacherID not exist", HttpStatus.BAD_REQUEST);
        }
    }

    //增加一个展示所有教师的功能
    @GetMapping("/showteachers")
    public ResponseEntity<List<Teacher>> getAllTeachers() {
        List<Teacher> teachers = teacherService.getAllTeachers();
        if (teachers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }
}
