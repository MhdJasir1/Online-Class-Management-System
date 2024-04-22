package com.jasir.springboot.onlineclassmanagment.domain.course_category.teacher_salary;

import com.jasir.springboot.onlineclassmanagment.domain.course_category.Months;
import com.jasir.springboot.onlineclassmanagment.domain.security.entity.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TeacherSalaryDTO {
    private String teacherCode;
    private String teacherName;
    private Double payment;
    private String paidDate;
    private String year;
    private Months paidMonth;
    private PaymentType paymentType;
}
