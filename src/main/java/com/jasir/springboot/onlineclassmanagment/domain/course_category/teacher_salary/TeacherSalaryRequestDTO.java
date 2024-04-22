package com.jasir.springboot.onlineclassmanagment.domain.course_category.teacher_salary;

import com.jasir.springboot.onlineclassmanagment.domain.course_category.Months;
import com.jasir.springboot.onlineclassmanagment.domain.security.entity.PaymentType;
import com.jasir.springboot.onlineclassmanagment.domain.security.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TeacherSalaryRequestDTO {

    @NotNull
    private Double payment;

    @NotBlank
    private String paidDate;

    @NotBlank
    private String year;

    private Months paidMonth;

    private PaymentType paymentType;
}
