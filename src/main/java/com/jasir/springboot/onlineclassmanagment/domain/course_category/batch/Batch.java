package com.jasir.springboot.onlineclassmanagment.domain.course_category.batch;

import com.jasir.springboot.onlineclassmanagment.domain.course_category.course.Course;
import com.jasir.springboot.onlineclassmanagment.domain.security.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "batch")
public class Batch {
    @Id
    @Column(name = "batch_id",nullable = false,updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long batchId;

    @Column(name = "name",length = 100, unique = true)
    private String name;

    @Column(name = "code",length = 25, unique = true)
    private String code;

    @Column(name = "year",length = 10)
    private String year;

    @Column(name = "fee")
    private Double fee;

    @Column(name = "status")
    private Integer status = 1;

    @Column(name = "course_id")
    private Long courseId;

    @ManyToOne
    @JoinColumn(name = "course_id",referencedColumnName = "course_id",insertable = false,updatable = false)
    private Course course;

    @Column(name = "teacher_id")
    private Long teacherId;

    @ManyToOne
    @JoinColumn(name = "teacher_id",referencedColumnName = "user_id",insertable = false,updatable = false)
    private User teacher;

    @ManyToMany(mappedBy = "studentBatch", fetch = FetchType.LAZY)
    private Set<User> students = new HashSet<>();
}
