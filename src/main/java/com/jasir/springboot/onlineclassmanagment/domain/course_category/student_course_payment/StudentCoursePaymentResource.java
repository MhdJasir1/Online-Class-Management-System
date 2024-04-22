package com.jasir.springboot.onlineclassmanagment.domain.course_category.student_course_payment;

import com.jasir.springboot.onlineclassmanagment.domain.course_category.batch.BatchWithTeacherDetailsDTO;
import com.jasir.springboot.onlineclassmanagment.dto.ApplicationResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/student-course-payment",produces = MediaType.APPLICATION_JSON_VALUE)
public class StudentCoursePaymentResource {

    private final StudentCoursePaymentService studentCoursePaymentService;

    @PostMapping("/add-payment/{student-code}")
    public ResponseEntity<ApplicationResponseDTO> addStudentCoursePayment(@PathVariable(name = "student-code") String studentCode, @Valid @RequestBody StudentCoursePaymentRequestDTO studentCoursePaymentRequestDTO){
        return ResponseEntity.ok(studentCoursePaymentService.addStudentCoursePayment(studentCode,studentCoursePaymentRequestDTO));
    }

    @PostMapping("/update-payment/{student-payment-id}")
    public ResponseEntity<ApplicationResponseDTO> updateStudentCoursePayment(@PathVariable(name = "student-payment-id") Long studentPaymentId, @Valid @RequestBody StudentCoursePaymentUpdateRequestDTO studentCoursePaymentUpdateRequestDTO){
        return ResponseEntity.ok(studentCoursePaymentService.updateStudentCoursePayment(studentPaymentId,studentCoursePaymentUpdateRequestDTO));
    }

    @DeleteMapping("/delete-payment/{student-payment-id}")
    public ResponseEntity<ApplicationResponseDTO> deleteCourse(@PathVariable(name = "student-payment-id") Long studentPaymentId){
        return ResponseEntity.ok(studentCoursePaymentService.deleteStudentCoursePayment(studentPaymentId));
    }

    @GetMapping("/all-payments")
    public ResponseEntity<List<StudentCoursePaymentDTO>> getAllPayments(){
        return ResponseEntity.ok(studentCoursePaymentService.getAllPayments());
    }

    @GetMapping("/all-payments-by-student-code/{studentCode}")
    public ResponseEntity<List<StudentCoursePaymentDTO>>getAllPaymentsByStudentCode(@PathVariable("studentCode") String studentCode){
        return ResponseEntity.ok(studentCoursePaymentService.getAllPaymentsByStudentCode(studentCode));
    }
}
