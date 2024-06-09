package com.example.controller;

import com.example.model.Appointment;
import com.example.model.AvailabilitySlot;
import com.example.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    // 学生发出预约请求
    @PostMapping("/request")

    public ResponseEntity<String> requestAppointment(@RequestBody Appointment appointment) {
        return appointmentService.requestAppointment(appointment);
    }
    //学生查看预约申请
    @GetMapping("/requestlist/{studentId}")
    public ResponseEntity<?> getStudentAppointments(@PathVariable int studentId) {
        List<Appointment> appointments = appointmentService.getStudentAppointments(studentId);
        return ResponseEntity.ok(appointments);
    }

    // 教师查看所有预约申请
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<?> getTeacherAppointments(@PathVariable int teacherId) {
        List<Appointment> appointments = appointmentService.getTeacherAppointments(teacherId);
        return ResponseEntity.ok(appointments);
    }

    // 教师处理预约申请
    @PutMapping("/update/{appointmentId}")
    public ResponseEntity<?> updateAppointmentStatus(@PathVariable int appointmentId, @RequestBody String status) {
        appointmentService.updateAppointmentStatus(appointmentId, status);
        return ResponseEntity.ok().build();
    }
    // 教师同意预约申请
    @PutMapping("/accept/{appointmentId}")
    public ResponseEntity<?> acceptAppointment(@PathVariable int appointmentId) {
        appointmentService.acceptAppointment(appointmentId);
        return ResponseEntity.ok().build();
    }
    // 教师拒绝预约申请
    @PutMapping("/refuse/{appointmentId}")
    public ResponseEntity<?> refuseAppointment(@PathVariable int appointmentId) {
        appointmentService.refuseAppointment(appointmentId);
        return ResponseEntity.ok().build();
    }

}
