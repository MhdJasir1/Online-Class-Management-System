package com.jasir.springboot.onlineclassmanagment.domain.course_category.student_course_payment;

import com.jasir.springboot.onlineclassmanagment.domain.course_category.batch.BatchRepository;
import com.jasir.springboot.onlineclassmanagment.domain.course_category.batch.BatchWithTeacherDetailsDTO;
import com.jasir.springboot.onlineclassmanagment.domain.course_category.student_batch.StudentBatchRepository;
import com.jasir.springboot.onlineclassmanagment.domain.security.entity.UserRole;
import com.jasir.springboot.onlineclassmanagment.domain.security.repos.UserRepository;
import com.jasir.springboot.onlineclassmanagment.domain.security.service.AuthService;
import com.jasir.springboot.onlineclassmanagment.dto.ApplicationResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentCoursePaymentService {

    private final StudentCoursePaymentRepository studentCoursePaymentRepository;
    private final UserRepository userRepository;
    private final StudentBatchRepository studentBatchRepository;
    private final BatchRepository batchRepository;

    public ApplicationResponseDTO addStudentCoursePayment(String studentCode, StudentCoursePaymentRequestDTO studentCoursePaymentRequestDTO) {

        if (userRepository.findByCodeAndUserRoleAndStatus(studentCode, UserRole.STUDENT, 1).isPresent()) {

            Long studentId = userRepository.findByCode(studentCode).get().getUserId();

            if (batchRepository.findByCode(studentCoursePaymentRequestDTO.getBatchCode()).isPresent()) {

                Long batchId = batchRepository.findByCode(studentCoursePaymentRequestDTO.getBatchCode()).get().getBatchId();

                String currentUser = AuthService.getCurrentUser();

                if (userRepository.findByUsernameAndUserRoleAndStatus(currentUser, UserRole.ADMIN, 1).isPresent()) {
                    //real admin
                    StudentCoursePayment studentCoursePayment = new StudentCoursePayment();
                    studentCoursePayment.setStudentId(studentId);
                    studentCoursePayment.setBatchId(batchId);
                    studentCoursePayment.setPaidDate(studentCoursePaymentRequestDTO.getPaidDate());
                    studentCoursePayment.setPaymentType(studentCoursePaymentRequestDTO.getPaymentType());
                    studentCoursePayment.setPayment(studentCoursePaymentRequestDTO.getPayment());
                    studentCoursePaymentRepository.save(studentCoursePayment);

                    return new ApplicationResponseDTO(HttpStatus.CREATED, "STUDENT_COURSE_PAYMENT_SUCCESSFUL", "Student course payment added successfully");
                } else {
                    //not an admin
                    return new ApplicationResponseDTO(HttpStatus.UNAUTHORIZED, "ACCESS_DENIED", "Access denied");
                }

            } else {
                return new ApplicationResponseDTO(HttpStatus.BAD_REQUEST, "INVALID_BATCH", "Invalid batch");
            }

        } else {
            return new ApplicationResponseDTO(HttpStatus.BAD_REQUEST, "INVALID_STUDENT", "Invalid student");
        }

    }

    public ApplicationResponseDTO updateStudentCoursePayment(Long studentPaymentId, StudentCoursePaymentUpdateRequestDTO studentCoursePaymentUpdateRequestDTO) {
        if (studentCoursePaymentRepository.findById(studentPaymentId).isPresent()) {

            String currentUser = AuthService.getCurrentUser();

            if (userRepository.findByUsernameAndUserRoleAndStatus(currentUser, UserRole.ADMIN, 1).isPresent()) {
                //real admin
                StudentCoursePayment studentCoursePayment = studentCoursePaymentRepository.findById(studentPaymentId).get();
                studentCoursePayment.setPaidDate(studentCoursePaymentUpdateRequestDTO.getPaidDate());
                studentCoursePayment.setPaymentType(studentCoursePaymentUpdateRequestDTO.getPaymentType());
                studentCoursePayment.setPayment(studentCoursePaymentUpdateRequestDTO.getPayment());
                studentCoursePaymentRepository.save(studentCoursePayment);

                return new ApplicationResponseDTO(HttpStatus.OK, "STUDENT_COURSE_PAYMENT_UPDATED", "Student course payment updated successfully");
            } else {
                //not an admin
                return new ApplicationResponseDTO(HttpStatus.UNAUTHORIZED, "ACCESS_DENIED", "Access denied");
            }


        } else {
            return new ApplicationResponseDTO(HttpStatus.BAD_REQUEST, "INVALID_STUDENT_PAYMENT", "Invalid student payment");
        }
    }

    public ApplicationResponseDTO deleteStudentCoursePayment(Long studentPaymentId) {
        if (studentCoursePaymentRepository.findById(studentPaymentId).isPresent()){
            String currentUser = AuthService.getCurrentUser();

            if (userRepository.findByUsernameAndUserRoleAndStatus(currentUser, UserRole.ADMIN, 1).isPresent()) {
                //real admin
                StudentCoursePayment studentCoursePayment = studentCoursePaymentRepository.findById(studentPaymentId).get();
                studentCoursePaymentRepository.delete(studentCoursePayment);

                return new ApplicationResponseDTO(HttpStatus.OK, "STUDENT_COURSE_PAYMENT_DELETED", "Student course payment deleted successfully");
            } else {
                //not an admin
                return new ApplicationResponseDTO(HttpStatus.UNAUTHORIZED, "ACCESS_DENIED", "Access denied");
            }
        }else{
            return new ApplicationResponseDTO(HttpStatus.BAD_REQUEST, "INVALID_STUDENT_PAYMENT", "Invalid student payment");
        }
    }

    public List<StudentCoursePaymentDTO> getAllPayments() {
        List<StudentCoursePaymentDTO> studentCoursePayments = studentCoursePaymentRepository.findStudentPayments();
        return studentCoursePayments;
    }

    public List<StudentCoursePaymentDTO> getAllPaymentsByStudentCode(String studentCode) {
        List<StudentCoursePaymentDTO> studentCoursePayments = studentCoursePaymentRepository.findStudentPaymentsByStudentCode(studentCode);
        return studentCoursePayments;
    }
}
