package com.jasir.springboot.onlineclassmanagment.domain.security.service;

import com.jasir.springboot.onlineclassmanagment.domain.security.domain.UserData;
import com.jasir.springboot.onlineclassmanagment.domain.security.dto.AuthResponseDTO;
import com.jasir.springboot.onlineclassmanagment.domain.security.dto.LogInDTO;
import com.jasir.springboot.onlineclassmanagment.domain.security.dto.UserDTO;
import com.jasir.springboot.onlineclassmanagment.domain.security.entity.Gender;
import com.jasir.springboot.onlineclassmanagment.domain.security.entity.User;
import com.jasir.springboot.onlineclassmanagment.domain.security.entity.UserRole;
import com.jasir.springboot.onlineclassmanagment.domain.security.repos.UserRepository;
import com.jasir.springboot.onlineclassmanagment.domain.security.util.JwtTokenUtil;
import com.jasir.springboot.onlineclassmanagment.dto.ApplicationResponseDTO;
import com.jasir.springboot.onlineclassmanagment.exception.ApplicationCustomException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.Console;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;
    private static Logger logger = LoggerFactory.getLogger(AuthService.class);


    public ApplicationResponseDTO signup(UserDTO userDTO) {
        if (userRepository.findByMobileOrUsername(userDTO.getMobile(), userDTO.getUsername()).isPresent()) {
            throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "USER_ALREADY_EXIST", "User Already Exist");
        }
        else {

            User user = new User();
            user.setUsername(userDTO.getUsername());
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            user.setUserRole(userDTO.getUserRole());
            user.setGender(userDTO.getGender());
            user.setMobile(userDTO.getMobile());
            user.setName(userDTO.getName());
            user.setRegisteredDate(String.valueOf(LocalDate.now()));

            String codeString = null;

            if (userDTO.getUserRole().equals(UserRole.ADMIN)){
                user.setStatus(1);
                codeString = "ADM_";
            } else if (userDTO.getUserRole().equals(UserRole.STUDENT)){
                user.setStatus(0);
                codeString = "STU_";
            } else if (userDTO.getUserRole().equals(UserRole.TEACHER)){
                user.setStatus(0);
                codeString = "TCH_";
            }

            if (!userRepository.findAll().isEmpty()) {

                Optional<User> stuCode = userRepository.findTopByOrderByUserIdDesc();
                Long nextUserId = stuCode.get().getUserId() + 1;
                int length = String.valueOf(nextUserId).length();


                if (length == 1){
                    codeString = codeString + "000000"+ nextUserId;
                }else if (length == 2){
                    codeString = codeString + "00000"+ nextUserId;
                }else if (length == 3){
                    codeString = codeString + "0000"+ nextUserId;
                }else if (length == 4){
                    codeString = codeString + "000"+ nextUserId;
                }else if (length == 5){
                    codeString = codeString + "00"+ nextUserId;
                }else if (length == 6){
                    codeString = codeString + "0"+ nextUserId;
                }else{
                    codeString = codeString + nextUserId;
                }

                user.setCode(codeString);
                userRepository.save(user);

                logger.info("Sign Up Success");

                return new ApplicationResponseDTO(HttpStatus.CREATED, "SIGNUP_SUCCESS", "Account created successfully");
            }else{

                codeString = codeString + "0000001";

                user.setCode(codeString);
                userRepository.save(user);

                logger.info("Sign Up Success");
                return new ApplicationResponseDTO(HttpStatus.CREATED, "SIGNUP_SUCCESS", "Account created successfully");
            }
        }

    }

    public AuthResponseDTO login(LogInDTO logInDTO) {
        Optional<User> optionalUser = userRepository.findByUsernameAndStatus(logInDTO.getUsername(),1);
        if (optionalUser.isEmpty()) {
            throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "INVALID_USERNAME_OR_PASSWORD", "Invalid Username or Password");
        } else {
            User user = optionalUser.get();
            if (!passwordEncoder.matches(logInDTO.getPassword(), user.getPassword())) {
                throw new ApplicationCustomException(HttpStatus.BAD_REQUEST, "INVALID_USERNAME_OR_PASSWORD", "Invalid Username or Password");
            }
            String accessToken = jwtTokenUtil.generateAccessToken(user);
            String refreshToken = jwtTokenUtil.generateRefreshToken(user);

//            user.setStatus(1);
//            userRepository.save(user);
            return new AuthResponseDTO(HttpStatus.OK, "LOGIN_SUCCESS", "Login Success", accessToken, refreshToken);
        }
    }

    public static String getCurrentUser() {
        try {
            SecurityContext securityContext = SecurityContextHolder.getContext();
            if (securityContext != null && securityContext.getAuthentication() != null) {
                Object principal = securityContext.getAuthentication().getPrincipal();
                if (principal instanceof UserData userData) {
                    return userData.getUsername();
                } else {
                    throw new ApplicationCustomException(HttpStatus.UNAUTHORIZED, "INVALID_PRINCIPAL", "Invalid Principal");
                }
            } else {
                throw new ApplicationCustomException(HttpStatus.UNAUTHORIZED, "SECURITY_CONTEXT_IS_NULL", "Security Context is Null");
            }
        } catch (Exception e) {
            throw new ApplicationCustomException(HttpStatus.UNAUTHORIZED, "INVALID_USER", e.getMessage());
        }
    }

    public AuthResponseDTO generateRefreshToken(String refreshToken) {
        if (jwtTokenUtil.validateToken(refreshToken)) {
            String username = jwtTokenUtil.getUsernameFromToken(refreshToken);
            User user = userRepository.findByUsernameAndStatus(username,1)
                    .orElseThrow(() -> new ApplicationCustomException(HttpStatus.BAD_REQUEST, "USER_NOT_FOUND", "User not Found"));
            String accessToken = jwtTokenUtil.generateAccessToken(user);
            String newRefreshToken = jwtTokenUtil.generateRefreshToken(user);
            return new AuthResponseDTO(HttpStatus.OK, "NEW_ACCESS_TOKEN_&_NEW_REFRESH_TOKEN", "New Access & Refresh Token generated", accessToken, newRefreshToken);
        }
        throw new ApplicationCustomException(HttpStatus.UNAUTHORIZED, "INVALID_REFRESH_TOKEN", "Invalid Refresh Token");
    }

}
