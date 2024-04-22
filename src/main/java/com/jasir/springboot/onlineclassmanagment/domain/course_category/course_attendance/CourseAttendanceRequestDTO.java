package com.jasir.springboot.onlineclassmanagment.domain.course_category.course_attendance;

import com.jasir.springboot.onlineclassmanagment.domain.course_category.course_time_schedule.CourseTimeSchedule;
import com.jasir.springboot.onlineclassmanagment.domain.security.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CourseAttendanceRequestDTO {

    private AttendanceStatus attendanceStatus;
    @NotBlank
    private String studentCode;
}
