package com.jasir.springboot.onlineclassmanagment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@Setter
public class ApplicationResponseDTO {
    private HttpStatus status;
    private String code;
    private String message;
}