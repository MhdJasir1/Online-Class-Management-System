package com.jasir.springboot.onlineclassmanagment.domain.course_category.batch;

import com.jasir.springboot.onlineclassmanagment.domain.course_category.DurationType;
import com.jasir.springboot.onlineclassmanagment.domain.course_category.course.Course;
import com.jasir.springboot.onlineclassmanagment.domain.security.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BatchRequestDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String batchCode;

    @NotBlank
    private String year;

    @NotNull
    @Min(500)
    private Double fee;

    @NotBlank
    private String courseCode;

    @NotBlank
    private String teacherCode;
}
