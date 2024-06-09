package com.example.mapper;

import com.example.model.Appointment;
import com.example.model.AvailabilitySlot;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface AppointmentMapper {
    //根据教师id查找预约
    List<Appointment> findAppointmentsByTeacherId(@Param("teacherId") int teacherId);
    //根据学生id查找预约
    List<Appointment> findAppointmentsByStudentId(@Param("studentId")int studentId);
    //新增预约记录
    void insertAppointment(Appointment appointment);
    //更新预约状态
    void updateAppointmentStatus(@Param("appointmentId") int appointmentId,@Param("status")  String status);

    void acceptAppointment(int appointmentId);

    void refuseAppointment(int appointmentId);


}



