package com.jasir.springboot.onlineclassmanagment.domain.course_category.student_course_payment;

import com.jasir.springboot.onlineclassmanagment.domain.course_category.batch.Batch;
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
@Table(name = "student_course_payment")
public class StudentCoursePayment {
    @Id
    @Column(name = "student_payment_id",nullable = false,updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentPaymentId;

    @Column(name = "payment")
    private Double payment;

    @Column(name = "paid_date",length = 15)
    private String paidDate;

    @Column(name = "student_id")
    private Long studentId;

    @ManyToOne
    @JoinColumn(name = "student_id",referencedColumnName = "user_id",insertable = false,updatable = false)
    private User student;

    @Column(name = "batch_id")
    private Long batchId;

    @ManyToOne
    @JoinColumn(name = "batch_id",referencedColumnName = "batch_id",insertable = false,updatable = false)
    private Batch batch;

    @Column(name = "payment_type",length = 15)
    @Enumerated(value = EnumType.STRING)
    private PaymentType paymentType;
}
