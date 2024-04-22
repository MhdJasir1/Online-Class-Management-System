package com.jasir.springboot.onlineclassmanagment.domain.course_category.teacher_salary;

import com.jasir.springboot.onlineclassmanagment.domain.course_category.Months;
import com.jasir.springboot.onlineclassmanagment.domain.security.entity.PaymentType;
import com.jasir.springboot.onlineclassmanagment.domain.security.entity.User;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "teacher_salary")
public class TeacherSalary {
    @Id
    @Column(name = "teacher_salary_id",nullable = false,updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentPaymentId;

    @Column(name = "payment")
    private Double payment;

    @Column(name = "year")
    private String year;

    @Column(name = "paid_date",length = 15)
    private String paidDate;

    @Column(name = "paid_month",length = 15)
    @Enumerated(EnumType.STRING)
    private Months paidMonth;

    @Column(name = "teacher_id")
    private Long teacherId;

    @ManyToOne
    @JoinColumn(name = "teacher_id",referencedColumnName = "user_id",insertable = false,updatable = false)
    private User teacher;

    @Column(name = "payment_type",length = 15)
    @Enumerated(value = EnumType.STRING)
    private PaymentType paymentType;
}
