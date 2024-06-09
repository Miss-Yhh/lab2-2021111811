package com.example.service;

import com.example.mapper.AdminMapper;
import com.example.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    private StudentMapper studentMapper;

    public HttpStatus login(String username, String password) {
        // 调用AdminMapper的login方法
        int result = studentMapper.login(username, password);
        // 根据返回的结果进行处理
        if (result > 0) {
            // 登录成功
            return HttpStatus.OK;
        } else {
            // 登录失败
            return HttpStatus.UNAUTHORIZED;
        }
    }
}
