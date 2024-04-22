package com.jasir.springboot.onlineclassmanagment.domain.course_category.course_time_schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseTimeScheduleRepository extends JpaRepository<CourseTimeSchedule,Long> {
    Optional<CourseTimeSchedule> findByBatchIdAndDateAndStartTime(Long batchId,String date, String startTime);
    Optional<CourseTimeSchedule> findByCourseTimeScheduleId(Long batchId);

    @Query("SELECT new com.jasir.springboot.onlineclassmanagment.domain.course_category.course_time_schedule.CourseTimeScheduleDTO(" +
            "c.courseTimeScheduleId, c.date, c.startTime, c.endTime,c.scheduleType,c.description,c.topic, b.name,b.code) FROM CourseTimeSchedule c JOIN c.batch b")
    List<CourseTimeScheduleDTO> findAllCourseTimeSchedules();
}
