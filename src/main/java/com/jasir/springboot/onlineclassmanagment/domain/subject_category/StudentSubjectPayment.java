//package com.jasir.springboot.onlineclassmanagment.domain.subject_category;
//
//import com.jasir.springboot.onlineclassmanagment.domain.course_category.Months;
//import com.jasir.springboot.onlineclassmanagment.domain.security.entity.PaymentType;
//import com.jasir.springboot.onlineclassmanagment.domain.security.entity.User;
//import jakarta.persistence.*;
//import lombok.*;
//
//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//@Setter
//@Builder
//@Entity
//@Table(name = "student_subject_payment")
//public class StudentSubjectPayment {
//    @Id
//    @Column(name = "student_payment_id",nullable = false,updatable = false)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long studentPaymentId;
//
//    @Column(name = "payment")
//    private Double payment;
//
//    @Column(name = "paid_date",length = 15)
//    private String paidDate;
//
//    @Column(name = "paid_month",length = 15)
//    @Enumerated(EnumType.STRING)
//    private Months paidMonth;
//
//    @Column(name = "student_id")
//    private Long studentId;
//
//    @ManyToOne
//    @JoinColumn(name = "student_id",referencedColumnName = "user_id",insertable = false,updatable = false)
//    private User student;
//
//    @Column(name = "payment_type",length = 15)
//    @Enumerated(value = EnumType.STRING)
//    private PaymentType paymentType;
//}
