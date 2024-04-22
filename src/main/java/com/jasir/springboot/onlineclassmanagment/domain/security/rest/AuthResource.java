package com.jasir.springboot.onlineclassmanagment.domain.security.rest;

import com.jasir.springboot.onlineclassmanagment.domain.security.dto.AuthResponseDTO;
import com.jasir.springboot.onlineclassmanagment.domain.security.dto.LogInDTO;
import com.jasir.springboot.onlineclassmanagment.domain.security.dto.UserDTO;
import com.jasir.springboot.onlineclassmanagment.domain.security.service.AuthService;
import com.jasir.springboot.onlineclassmanagment.dto.ApplicationResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthResource {
    private final AuthService authService;
    @PostMapping("/signup")
    public ResponseEntity<ApplicationResponseDTO> signup(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(authService.signup(userDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LogInDTO logInDTO) {
        return ResponseEntity.ok(authService.login(logInDTO));
    }

    @PostMapping("/token/refresh/{refresh-token}")
    public ResponseEntity<AuthResponseDTO> refreshAccessToken(@PathVariable("refresh-token") String refreshToken) {
        return ResponseEntity.ok(authService.generateRefreshToken(refreshToken));
    }

}
