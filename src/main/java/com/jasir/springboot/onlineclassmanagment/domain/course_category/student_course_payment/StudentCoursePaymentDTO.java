package com.jasir.springboot.onlineclassmanagment.domain.course_category.student_course_payment;


import com.jasir.springboot.onlineclassmanagment.domain.security.entity.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentCoursePaymentDTO {
    private String paidDate;
    private Double payment;
    private PaymentType paymentType;
    private String studentCode;
    private String studentName;
    private String batchCode;
    private String batchName;
}
