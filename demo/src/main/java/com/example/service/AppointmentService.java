package com.example.service;

import com.example.mapper.AppointmentMapper;
import com.example.model.Appointment;
import com.example.model.AvailabilitySlot;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.mapper.AppointmentMapper;
import com.example.mapper.AvailabilitySlotsMapper;
import com.example.model.Appointment;
import com.example.model.AvailabilitySlot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AppointmentService {
    @Autowired
    private AppointmentMapper appointmentMapper; // 确保AppointmentMapper的路径正确

    @Autowired
    private AvailabilitySlotsMapper availabilitySlotsMapper; // 确保AvailabilitySlotMapper的路径正确

    public List<AvailabilitySlot> getAvailableSlots(int teacherId) {
        // 确保findAvailableSlotsByTeacherId方法在Mapper中定义和实现
        return availabilitySlotsMapper.findAvailableSlotsByTeacherId(teacherId);
    }

    public ResponseEntity<String> requestAppointment(Appointment appointment) {
        // 查询对应slot_id的时间段状态
        String slotStatus = availabilitySlotsMapper.getSlotStatus(appointment.getSlotId());

        // 判断时间段状态是否为"AVAILABLE"
        if ("AVAILABLE".equals(slotStatus)) {
            // 如果是"AVAILABLE"，执行预约操作
            appointmentMapper.insertAppointment(appointment);
            return ResponseEntity.ok("succeed!");
        } else {
            return ResponseEntity.badRequest().body("invalid time");
        }
    }

    public List<Appointment> getTeacherAppointments(int teacherId) {
        // 确保findAppointmentsByTeacherId方法在Mapper中定义和实现
        return appointmentMapper.findAppointmentsByTeacherId(teacherId);
    }

    public List<Appointment> getStudentAppointments(int studentId) {
        // 确保findAppointmentsByTeacherId方法在Mapper中定义和实现
        return appointmentMapper.findAppointmentsByStudentId(studentId);
    }
    public void updateAppointmentStatus(int appointmentId, String status) {
        if (!"REQUESTED".equals(status) && !"APPROVED".equals(status) && !"DENIED".equals(status) && !"CANCELED".equals(status)) {
            throw new IllegalArgumentException("Invalid status value");
        }
        appointmentMapper.updateAppointmentStatus(appointmentId, status);
    }

    public void acceptAppointment(int appointmentId) {
        appointmentMapper.acceptAppointment(appointmentId);
    }

    public void refuseAppointment(int appointmentId) {
        appointmentMapper.refuseAppointment(appointmentId);
    }

}


