package com.example.mapper;

import com.example.model.Teacher;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TeacherMapper {
    int login(@Param("username") String username, @Param("password") String password);

    int addTeacher(Teacher teacher);

    int deleteTeacher(@Param("teacherId") int teacherId);

    List<Teacher> findAll();
}
