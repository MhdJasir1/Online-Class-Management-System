package com.jasir.springboot.onlineclassmanagment.domain.security.service;

import com.jasir.springboot.onlineclassmanagment.domain.course_category.all_users.AllUserDTO;
import com.jasir.springboot.onlineclassmanagment.domain.course_category.batch.BatchRepository;
import com.jasir.springboot.onlineclassmanagment.domain.course_category.student_batch.StudentBatchRepository;
import com.jasir.springboot.onlineclassmanagment.domain.security.admin.StudentBatchRequestDTO;
import com.jasir.springboot.onlineclassmanagment.domain.security.entity.User;
import com.jasir.springboot.onlineclassmanagment.domain.security.entity.UserRole;
import com.jasir.springboot.onlineclassmanagment.domain.security.repos.UserRepository;
import com.jasir.springboot.onlineclassmanagment.dto.ApplicationResponseDTO;
import com.jasir.springboot.onlineclassmanagment.exception.ApplicationCustomException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final StudentBatchRepository studentBatchRepository;
    private final BatchRepository batchRepository;
    private final UserRepository userRepository;

    public User findByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsernameAndStatus(username, 1);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new ApplicationCustomException(HttpStatus.UNAUTHORIZED, "USER_NOT_FOUND", "User not found");
        }
    }

    public ApplicationResponseDTO verifyTeacher(String teacherId) {
        String currentUser = AuthService.getCurrentUser();

        if (userRepository.findByUsernameAndUserRoleAndStatus(currentUser, UserRole.ADMIN, 1).isPresent()) {
            //real admin
            if (userRepository.findByCodeAndUserRole(teacherId, UserRole.TEACHER).isPresent()) {
                //teacher present
                User teacher = userRepository.findByCode(teacherId).get();
                teacher.setStatus(1);
                userRepository.save(teacher);

                return new ApplicationResponseDTO(HttpStatus.OK, "TEACHER_VERIFIED", "Teacher verified");
            } else {
                //teacher not found
                return new ApplicationResponseDTO(HttpStatus.NOT_FOUND, "TEACHER_NOT_FOUND", "Teacher not Found");
            }
        } else {
            //not an admin
            return new ApplicationResponseDTO(HttpStatus.UNAUTHORIZED, "ACCESS_DENIED", "Access denied");
        }
    }

    public ApplicationResponseDTO verifyStudent(String studentCode) {
        String currentUser = AuthService.getCurrentUser();

        if (userRepository.findByUsernameAndUserRoleAndStatus(currentUser, UserRole.ADMIN, 1).isPresent()) {
            //real admin
            if (userRepository.findByCodeAndUserRole(studentCode, UserRole.STUDENT).isPresent()) {
                //student present
                User student = userRepository.findByCode(studentCode).get();
                student.setStatus(1);
                userRepository.save(student);

                return new ApplicationResponseDTO(HttpStatus.OK, "STUDENT_VERIFIED", "Student verified");
            } else {
                //teacher not found
                return new ApplicationResponseDTO(HttpStatus.NOT_FOUND, "STUDENT_NOT_FOUND", "Student not Found");
            }
        } else {
            //not an admin
            return new ApplicationResponseDTO(HttpStatus.UNAUTHORIZED, "ACCESS_DENIED", "Access denied");
        }
    }

    public List<AllUserDTO> getAllStudents(UserRole student) {
        List<AllUserDTO> allStudents = userRepository.findAllStudents(student);
        return allStudents;
    }

    public List<AllUserDTO> getAllStudentsByStudentCode(UserRole student, String studentCode) {
        List<AllUserDTO> allStudentsByStudentCode = userRepository.findAllStudentsByStudentCode(student, studentCode);
        return allStudentsByStudentCode;
    }

    public List<AllUserDTO> getAllTeachers(UserRole teacher) {
        List<AllUserDTO> allTeachers = userRepository.findAllTeachers(teacher);
        return allTeachers;
    }

    public List<AllUserDTO> getAllTeachersByTeacherCode(UserRole teacher, String teacherCode) {
        List<AllUserDTO> allTeachersByTeacherCode = userRepository.findAllTeachersByTeacherCode(teacher, teacherCode);
        return allTeachersByTeacherCode;
    }

    public ApplicationResponseDTO assignStudentToBatch(String studentCode, StudentBatchRequestDTO studentBatchRequestDTO) {

        if (batchRepository.findByCode(studentBatchRequestDTO.getBatchCode()).isPresent()) {

            Long batchId = batchRepository.findByCode(studentBatchRequestDTO.getBatchCode()).get().getBatchId();

            String currentUser = AuthService.getCurrentUser();

            if (userRepository.findByUsernameAndUserRoleAndStatus(currentUser, UserRole.ADMIN, 1).isPresent()) {
                //real admin
                if (userRepository.findByCodeAndUserRoleAndStatus(studentCode, UserRole.STUDENT, 1).isPresent()) {
                    //student present
                    Long studentId = userRepository.findByCodeAndUserRoleAndStatus(studentCode, UserRole.STUDENT, 1).get().getUserId();

                    if (studentBatchRepository.findByStudentCodeAndBatchCode(studentCode,studentBatchRequestDTO.getBatchCode()).isEmpty()){

                        studentBatchRepository.saveStudentBatch(studentId,batchId);

                        return new ApplicationResponseDTO(HttpStatus.OK, "STUDENT_ASSIGNED", "Student assigned to batch successfully");
                    }else{
                        return new ApplicationResponseDTO(HttpStatus.BAD_REQUEST, "STUDENT_ALREADY_ASSIGNED", "Student already assigned to batch");
                    }
                } else {
                    //student not found
                    return new ApplicationResponseDTO(HttpStatus.NOT_FOUND, "STUDENT_NOT_FOUND", "Student not Found");
                }
            } else {
                //not an admin
                return new ApplicationResponseDTO(HttpStatus.UNAUTHORIZED, "ACCESS_DENIED", "Access denied");
            }
        } else {
            return new ApplicationResponseDTO(HttpStatus.NOT_FOUND, "BATCH_NOT_FOUND", "Batch not Found");

        }

    }
}