package com.example.service;

import com.example.mapper.AvailabilitySlotsMapper;
import com.example.model.AvailabilitySlot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class AvailabilitySlotsService {

    @Autowired
    private AvailabilitySlotsMapper availabilitySlotsMapper;

    //创建一个空闲时间段
    public void createAvailabilitySlot(AvailabilitySlot availabilitySlot) {

        availabilitySlotsMapper.insertAvailabilitySlot(availabilitySlot);
    }
    //更新空闲时间段
    public void updateAvailabilitySlotStatus(int slotId, String status) {
        availabilitySlotsMapper.updateAvailabilitySlotStatus(slotId, status);
    }
    //根据教师id和起止时间 查询状态
    public List<AvailabilitySlot> selectAvailabilitySlotsByTeacherIdAndTime(int teacherId, Date startTime, Date endTime) {
        System.out.println(availabilitySlotsMapper.selectAvailabilitySlotsByTeacherIdAndTime(teacherId, startTime, endTime));
        return availabilitySlotsMapper.selectAvailabilitySlotsByTeacherIdAndTime(teacherId, startTime, endTime);

    }
    //根据教师ID查找可用的空闲时间槽
    public List<AvailabilitySlot> findAvailableSlotsByTeacherId(int teacherId) {
        return availabilitySlotsMapper.findAvailableSlotsByTeacherId(teacherId);
    }
//    @Override
//    public List<AvailabilitySlot> getAvailabilitySlots() {
//        return availabilitySlotsMapper.selectAvailabilitySlots(); // 假设Mapper中有这个方法
//    }
}
