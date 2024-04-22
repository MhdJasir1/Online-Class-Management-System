package com.jasir.springboot.onlineclassmanagment.domain.course_category.all_users.student.student_course_temp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentCourseRepository extends JpaRepository<StudentCourse,Long> {
    Optional<StudentCourse> findByStudentIdAndCourseId(Long studentId,Long courseId);
}
