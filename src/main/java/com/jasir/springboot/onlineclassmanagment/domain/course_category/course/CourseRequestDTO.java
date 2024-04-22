package com.jasir.springboot.onlineclassmanagment.domain.course_category.course;

import com.jasir.springboot.onlineclassmanagment.domain.course_category.DurationType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CourseRequestDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String code;

    @NotBlank
    private String content;

    @NotBlank
    private String description;

    @NotNull
    @Min(value = 1)
    private Integer duration;

    private DurationType durationType;
}
