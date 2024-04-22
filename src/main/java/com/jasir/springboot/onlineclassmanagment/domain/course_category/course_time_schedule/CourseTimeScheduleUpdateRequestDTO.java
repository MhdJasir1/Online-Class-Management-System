package com.jasir.springboot.onlineclassmanagment.domain.course_category.course_time_schedule;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CourseTimeScheduleUpdateRequestDTO {

    @NotBlank
    private String startTime;

    @NotBlank
    private String endTime;

    @NotBlank
    private String date;

    private TimeScheduleType timeScheduleStatus;

    @NotBlank
    private String description;

    @NotBlank
    private String topic;
}
