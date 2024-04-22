package com.jasir.springboot.onlineclassmanagment.domain.course_category.course_attendance;

import com.jasir.springboot.onlineclassmanagment.domain.course_category.batch.BatchWithTeacherDetailsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseAttendanceRepository extends JpaRepository<CourseAttendance,Long> {
    Optional<CourseAttendance> findByCourseTimeScheduleIdAndStudentId(Long courseTimeScheduleId,Long studentId);

    @Query("SELECT new com.jasir.springboot.onlineclassmanagment.domain.course_category.course_attendance.CourseAttendanceDTO(" +
            "ca.courseAttendanceId,ca.attendanceStatus,ca.courseTimeScheduleId,cts.startTime,cts.endTime," +
            "cts.date,cts.scheduleType,cts.topic, b.batchId,b.name,b.code,st.userId,st.code,st.name) " +
            "FROM CourseAttendance ca JOIN ca.student st JOIN ca.courseTimeSchedule cts JOIN cts.batch b")
    List<CourseAttendanceDTO> findCourseAttendanceDetails();
    @Query("SELECT new com.jasir.springboot.onlineclassmanagment.domain.course_category.course_attendance.CourseAttendanceDTO(" +
            "ca.courseAttendanceId,ca.attendanceStatus,ca.courseTimeScheduleId,cts.startTime," +
            "cts.endTime,cts.date,cts.scheduleType,cts.topic, b.batchId,b.name,b.code,st.userId," +
            "st.code,st.name) FROM CourseAttendance ca JOIN ca.student st JOIN ca.courseTimeSchedule cts JOIN cts.batch b WHERE cts.date LIKE %:date%")
    List<CourseAttendanceDTO> findCourseAttendanceDetailsByDate(@Param("date") String date);
    @Query("SELECT new com.jasir.springboot.onlineclassmanagment.domain.course_category.course_attendance.CourseAttendanceDTO(" +
            "ca.courseAttendanceId,ca.attendanceStatus,ca.courseTimeScheduleId,cts.startTime," +
            "cts.endTime,cts.date,cts.scheduleType,cts.topic, b.batchId,b.name,b.code,st.userId," +
            "st.code,st.name) FROM CourseAttendance ca JOIN ca.student st JOIN ca.courseTimeSchedule cts JOIN cts.batch b WHERE st.code LIKE :studentCode")
    List<CourseAttendanceDTO> findCourseAttendanceDetailsByStudentCode(@Param("studentCode") String studentCode);
}
