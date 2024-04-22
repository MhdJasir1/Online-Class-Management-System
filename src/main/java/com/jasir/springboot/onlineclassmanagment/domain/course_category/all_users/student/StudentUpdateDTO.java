package com.jasir.springboot.onlineclassmanagment.domain.course_category.all_users.student;

import com.jasir.springboot.onlineclassmanagment.domain.security.entity.Gender;
import com.jasir.springboot.onlineclassmanagment.domain.security.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentUpdateDTO {
    private String name;
    private String mobile;
}
