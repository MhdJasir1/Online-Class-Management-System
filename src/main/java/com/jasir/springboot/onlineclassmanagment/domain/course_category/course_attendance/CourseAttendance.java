package com.jasir.springboot.onlineclassmanagment.domain.course_category.course_attendance;

import com.jasir.springboot.onlineclassmanagment.domain.course_category.course_time_schedule.CourseTimeSchedule;
import com.jasir.springboot.onlineclassmanagment.domain.security.entity.User;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "course_attendance")
public class CourseAttendance {
    @Id
    @Column(name = "course_attendance_id",nullable = false,updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseAttendanceId;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private AttendanceStatus attendanceStatus;

    @Column(name = "student_id")
    private Long studentId;

    @ManyToOne
    @JoinColumn(name = "student_id",referencedColumnName = "user_id",insertable = false,updatable = false)
    private User student;

    @Column(name = "course_time_schedule_id")
    private Long courseTimeScheduleId;

    @ManyToOne
    @JoinColumn(name = "course_time_schedule_id",referencedColumnName = "course_time_schedule_id",insertable = false,updatable = false)
    private CourseTimeSchedule courseTimeSchedule;
}
