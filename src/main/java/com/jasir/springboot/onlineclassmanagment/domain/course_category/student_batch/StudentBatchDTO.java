package com.jasir.springboot.onlineclassmanagment.domain.course_category.student_batch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentBatchDTO {
    private String studentCode;
    private String batchCode;
}
