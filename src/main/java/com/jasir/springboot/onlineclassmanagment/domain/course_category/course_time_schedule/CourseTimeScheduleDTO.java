package com.jasir.springboot.onlineclassmanagment.domain.course_category.course_time_schedule;

import com.jasir.springboot.onlineclassmanagment.domain.course_category.batch.Batch;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CourseTimeScheduleDTO {
    private Long courseTimeScheduleId;
    private String date;
    private String startTime;
    private String endTime;
    private TimeScheduleType scheduleType;
    private String description;
    private String topic;
    private String batchName;
    private String batchCode;
}
