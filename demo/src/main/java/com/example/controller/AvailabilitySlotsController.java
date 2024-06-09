package com.example.controller;

import com.example.model.AvailabilitySlot;
import com.example.service.AvailabilitySlotsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/availabilitySlots")
public class AvailabilitySlotsController {

    @Autowired
    private AvailabilitySlotsService availabilitySlotsService;

    @PostMapping("/create")//创建一个时间段，默认为空闲
    public ResponseEntity<String> createAvailabilitySlot(@RequestBody AvailabilitySlot availabilitySlot) {
        availabilitySlotsService.createAvailabilitySlot(availabilitySlot);
        return new ResponseEntity<>("Availability slot created successfully", HttpStatus.CREATED);
    }

    @PutMapping("/updateStatus/{slotId}/{status}")//更新某时间段状态
    public ResponseEntity<String> updateAvailabilitySlotStatus(@PathVariable int slotId, @PathVariable String status) {
        availabilitySlotsService.updateAvailabilitySlotStatus(slotId, status);
        return new ResponseEntity<>("Availability slot status updated successfully", HttpStatus.OK);
    }

    //由teacherId获取空闲时间段
    @GetMapping("/available/{teacherId}")
    public ResponseEntity<?> getAvailableSlots(@PathVariable int teacherId) {

        List<AvailabilitySlot> slots = availabilitySlotsService.findAvailableSlotsByTeacherId(teacherId);
        return ResponseEntity.ok(slots);
    }

    // 假设日期字符串的格式是 "yyyy-MM-dd HH:mm:ss"
    public Date convertStringToDate(String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(dateStr);
    }

    @GetMapping("/availabilitylist")
    public ResponseEntity<List<AvailabilitySlot>> listAvailabilitySlotsByTeacherIdAndTime(
            @RequestParam("teacherId") int teacherId,
            @RequestParam("startTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") String startTimeStr,
            @RequestParam("endTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") String endTimeStr) {

        try {
            Date startTime = convertStringToDate(startTimeStr);
            Date endTime = convertStringToDate(endTimeStr);

            List<AvailabilitySlot> availabilitySlots = availabilitySlotsService.selectAvailabilitySlotsByTeacherIdAndTime(teacherId, startTime, endTime);

            if (availabilitySlots != null && !availabilitySlots.isEmpty()) {
                return new ResponseEntity<>(availabilitySlots, HttpStatus.OK);
            } else {
                // 根据你的业务需求，可以选择返回空列表或者特定的错误状态码
                // 这里我返回空列表，但状态码仍然是OK，因为查询本身没有错误
                return new ResponseEntity<>( HttpStatus.OK);
            }

        } catch (ParseException e) {
            // 日期格式不正确，返回错误状态码
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // 其他可能的请求映射方法..
    }
}
