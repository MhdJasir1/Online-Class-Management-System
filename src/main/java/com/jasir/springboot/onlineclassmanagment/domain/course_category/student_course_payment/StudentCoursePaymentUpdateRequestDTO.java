package com.jasir.springboot.onlineclassmanagment.domain.course_category.student_course_payment;

import com.jasir.springboot.onlineclassmanagment.domain.security.entity.PaymentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentCoursePaymentUpdateRequestDTO {

    @NotNull
    private Double payment;
    @NotBlank
    private String paidDate;
    private PaymentType paymentType;

}
