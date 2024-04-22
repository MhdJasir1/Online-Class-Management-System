package com.jasir.springboot.onlineclassmanagment.domain.course_category.all_users.student.teacher_course_temp;

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
@Table(name = "teacher_course")
public class TeacherCourse {

    @Id
    @Column(name = "teacher_course_id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teacherCourseId;

    @Column(name = "teacher_id")
    private Long teacherId;

    @ManyToOne
    @JoinColumn(name = "teacher_id",referencedColumnName = "user_id",insertable = false,updatable = false)
    private User teacher;

    @Column(name = "course_id")
    private Long courseId;

    @ManyToOne
    @JoinColumn(name = "course_id",referencedColumnName = "course_id",insertable = false,updatable = false)
    private Course course;

}
