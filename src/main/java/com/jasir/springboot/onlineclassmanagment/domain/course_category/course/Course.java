package com.jasir.springboot.onlineclassmanagment.domain.course_category.course;

import com.jasir.springboot.onlineclassmanagment.domain.course_category.DurationType;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "course")
public class Course {
    @Id
    @Column(name = "course_id",nullable = false,updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;

    @Column(name = "name",length = 100, unique = true)
    private String name;

    @Column(name = "code",length = 25, unique = true)
    private String code;

    @Column(name = "content",columnDefinition = "text")
    private String content;

    @Column(name = "description",columnDefinition = "text")
    private String description;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "status")
    private Integer status = 1;

    @Column(name = "duration_type",length = 20)
    @Enumerated(EnumType.STRING)
    private DurationType durationType;
}
