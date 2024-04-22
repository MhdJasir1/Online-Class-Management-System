package com.jasir.springboot.onlineclassmanagment.domain.course_category.all_users;

import com.jasir.springboot.onlineclassmanagment.domain.security.entity.Gender;
import com.jasir.springboot.onlineclassmanagment.domain.security.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AllUserDTO {
    private String code;
    private String name;
    private String mobile;
    private Gender gender;
    private UserRole userRole;
    private String registeredDate;
    private Integer status;
}
