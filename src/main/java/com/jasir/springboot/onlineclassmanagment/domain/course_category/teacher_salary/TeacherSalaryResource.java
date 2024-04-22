package com.jasir.springboot.onlineclassmanagment.domain.course_category.teacher_salary;

import com.jasir.springboot.onlineclassmanagment.domain.course_category.student_course_payment.StudentCoursePaymentDTO;
import com.jasir.springboot.onlineclassmanagment.domain.course_category.student_course_payment.StudentCoursePaymentRequestDTO;
import com.jasir.springboot.onlineclassmanagment.domain.course_category.student_course_payment.StudentCoursePaymentUpdateRequestDTO;
import com.jasir.springboot.onlineclassmanagment.dto.ApplicationResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/teacher-salary",produces = MediaType.APPLICATION_JSON_VALUE)
public class TeacherSalaryResource {

    private final TeacherSalaryService teacherSalaryService;

    @PostMapping("/add-payment/{teacher-code}")
    public ResponseEntity<ApplicationResponseDTO> addTeacherSalary(@PathVariable(name = "teacher-code") String teacherCode, @Valid @RequestBody TeacherSalaryRequestDTO teacherSalaryRequestDTO){
        return ResponseEntity.ok(teacherSalaryService.addTeacherSalary(teacherCode,teacherSalaryRequestDTO));
    }

    @PostMapping("/update-payment/{teacher-salary-id}")
    public ResponseEntity<ApplicationResponseDTO> updateTeacherSalary(@PathVariable(name = "teacher-salary-id") Long teacherSalaryId, @Valid @RequestBody TeacherSalaryRequestDTO teacherSalaryRequestDTO){
        return ResponseEntity.ok(teacherSalaryService.updateTeacherSalary(teacherSalaryId,teacherSalaryRequestDTO));
    }

    @DeleteMapping("/delete-payment/{teacher-salary-id}")
    public ResponseEntity<ApplicationResponseDTO> deleteTeacherSalary(@PathVariable(name = "teacher-salary-id") Long teacherSalaryId){
        return ResponseEntity.ok(teacherSalaryService.deleteTeacherSalary(teacherSalaryId));
    }

    @GetMapping("/all-payments")
    public ResponseEntity<List<TeacherSalaryDTO>> getAllPayments(){
        return ResponseEntity.ok(teacherSalaryService.getAllPayments());
    }

    @GetMapping("/all-payments-by-teacher-code/{teacherCode}")
    public ResponseEntity<List<TeacherSalaryDTO>>getAllPaymentsByTeacherCode(@PathVariable("teacherCode") String teacherCode){
        return ResponseEntity.ok(teacherSalaryService.getAllPaymentsByTeacherCode(teacherCode));
    }
}
