package com.jasir.springboot.onlineclassmanagment.domain.course_category.batch;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BatchUpdateRequestDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String year;

    @NotNull
    @Min(500)
    private Double fee;

    @NotBlank
    private String teacherCode;
}
