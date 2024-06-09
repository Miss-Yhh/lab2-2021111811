package com.example.service;

import com.example.mapper.AdminMapper;
import com.example.mapper.TeacherMapper;
import com.example.model.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;
    public HttpStatus login(String username, String password) {
        // 调用AdminMapper的login方法
        int result = teacherMapper.login(username, password);
        // 根据返回的结果进行处理
        if (result > 0) {
            // 登录成功
            return HttpStatus.OK;
        } else {
            // 登录失败
            return HttpStatus.UNAUTHORIZED;
        }
    }
    public void addTeacher(Teacher teacher) {
        teacherMapper.addTeacher(teacher);
    }
    public int deleteTeacher(int teacherId) {
        return teacherMapper.deleteTeacher(teacherId);
    }
    public List<Teacher> getAllTeachers(){
        return teacherMapper.findAll();
    }

}
