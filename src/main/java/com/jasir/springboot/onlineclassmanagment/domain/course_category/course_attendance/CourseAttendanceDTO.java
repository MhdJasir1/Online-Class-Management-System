package com.jasir.springboot.onlineclassmanagment.domain.course_category.course_attendance;

import com.jasir.springboot.onlineclassmanagment.domain.course_category.course_time_schedule.TimeScheduleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CourseAttendanceDTO {

    private Long courseAttendanceId;
    private AttendanceStatus attendanceStatus;

    //time schedule details
    private Long courseTimeScheduleId;
    private String startTime;
    private String endTime;
    private String date;
    private TimeScheduleType scheduleType;
    private String topic;

    //batch details
    private Long batchId;
    private String batchName;
    private String batchCode;

    //student details
    private Long studentId;
    private String studentCode;
    private String studentName;

}
