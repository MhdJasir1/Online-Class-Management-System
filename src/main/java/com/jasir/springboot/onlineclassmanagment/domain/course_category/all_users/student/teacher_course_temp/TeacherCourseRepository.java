package com.jasir.springboot.onlineclassmanagment.domain.course_category.all_users.student.teacher_course_temp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherCourseRepository extends JpaRepository<TeacherCourse,Long> {
    Optional<TeacherCourse> findByTeacherIdAndCourseId(Long teacherId,Long courseId);
}
