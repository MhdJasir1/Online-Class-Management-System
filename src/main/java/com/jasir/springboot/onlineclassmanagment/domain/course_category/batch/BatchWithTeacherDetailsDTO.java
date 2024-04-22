package com.jasir.springboot.onlineclassmanagment.domain.course_category.batch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BatchWithTeacherDetailsDTO {
    private Long batchId;
    private String name;
    private String code;
    private String year;
    private Double fee;
    private Integer status;
    private String courseCode;
    private String courseName;
    private String teacherCode;
    private String teacherName;
}
