package com.jasir.springboot.onlineclassmanagment.domain.course_category.all_users.student.student_course_temp;

import com.jasir.springboot.onlineclassmanagment.domain.course_category.course.Course;
import com.jasir.springboot.onlineclassmanagment.domain.security.entity.User;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "student_course")
public class StudentCourse {

    @Id
    @Column(name = "student_course_id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentCourseId;

    @Column(name = "student_id")
    private Long studentId;

    @ManyToOne
    @JoinColumn(name = "student_id",referencedColumnName = "user_id",insertable = false,updatable = false)
    private User student;

    @Column(name = "course_id")
    private Long courseId;

    @ManyToOne
    @JoinColumn(name = "course_id",referencedColumnName = "course_id",insertable = false,updatable = false)
    private Course course;

}
