package com.jasir.springboot.onlineclassmanagment.domain.security.entity;

import com.jasir.springboot.onlineclassmanagment.domain.course_category.batch.Batch;
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
@Table(name = "\"User\"")
public class User {

    @Id
    @Column(name = "user_id", nullable = false, updatable = false)
//    @SequenceGenerator(
//            name = "primary_sequence",
//            sequenceName = "primary_sequence",
//            allocationSize = 1,
//            initialValue = 10000
//    )
//    @GeneratedValue(
//            strategy = GenerationType.SEQUENCE,
//            generator = "primary_sequence"
//    )
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "code", length = 20)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "user_role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole userRole = UserRole.STUDENT;

    @Column(name = "mobile", length = 10, unique = true)
    private String mobile;

    @Column(name = "registered_date", length = 15)
    private String registeredDate;

    @Column(name = "gender",length = 10)
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @Column(name = "status")
    private Integer status = 0;

    @ManyToMany
    @JoinTable(
            name = "student_batch",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "batch_id"))
    private Set<Batch> studentBatch = new HashSet<>();

}
