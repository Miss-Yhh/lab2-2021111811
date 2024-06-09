package com.example.mapper;

import com.example.model.AvailabilitySlot;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;


@Mapper
public interface AvailabilitySlotsMapper {
    // 插入一个空闲时间段
    int insertAvailabilitySlot(AvailabilitySlot availabilitySlot);

    // 更新一个空闲时间段的状态（例如：预留或拒绝）
    int updateAvailabilitySlotStatus(@Param("slotId") int slotId, @Param("status") String status);

    // 根据教师ID和时间段查询空闲时间段
    List<AvailabilitySlot> selectAvailabilitySlotsByTeacherIdAndTime(@Param("teacherId") int teacherId,
                                                                     @Param("startTime") Date startTime,
                                                                     @Param("endTime") Date endTime);
    //根据教师ID查询空闲时间段
    List<AvailabilitySlot> findAvailableSlotsByTeacherId(@Param("teacherId") int teacherId);

    String getSlotStatus(int slotId);
    // 其他可能的CRUD操作...
}
