package com.jasir.springboot.onlineclassmanagment.domain.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LogInDTO {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
