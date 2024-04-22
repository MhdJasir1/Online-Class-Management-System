package com.jasir.springboot.onlineclassmanagment.domain.security.dto;

import com.jasir.springboot.onlineclassmanagment.domain.security.entity.Gender;
import com.jasir.springboot.onlineclassmanagment.domain.security.entity.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {
    private Gender gender;
    private UserRole userRole;
    @NotBlank
    private String mobile;
    @NotBlank
    private String name;
    @NotBlank
    private String password;
    @NotBlank
    private String username;

}
