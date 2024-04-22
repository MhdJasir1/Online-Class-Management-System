package com.jasir.springboot.onlineclassmanagment.domain.course_category.all_users;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SelectCourseDTO {
    @NotBlank
    private String courseCode;
}
