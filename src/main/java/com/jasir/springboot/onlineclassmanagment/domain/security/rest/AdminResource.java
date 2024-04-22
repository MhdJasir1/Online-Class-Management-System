package com.jasir.springboot.onlineclassmanagment.domain.security.rest;

import com.jasir.springboot.onlineclassmanagment.domain.course_category.all_users.AllUserDTO;
import com.jasir.springboot.onlineclassmanagment.domain.security.admin.StudentBatchRequestDTO;
import com.jasir.springboot.onlineclassmanagment.domain.security.entity.UserRole;
import com.jasir.springboot.onlineclassmanagment.domain.security.service.UserService;
import com.jasir.springboot.onlineclassmanagment.dto.ApplicationResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/admin" ,produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminResource {
    private final UserService userService;

    @PostMapping("/verify-teacher/{teacher-id}")
    public ResponseEntity<ApplicationResponseDTO> verifyTeacher(@PathVariable("teacher-id") String teacherId) {
        return ResponseEntity.ok(userService.verifyTeacher(teacherId));
    }

    @PostMapping("/verify-student/{student-code}")
    public ResponseEntity<ApplicationResponseDTO> verifyStudent(@PathVariable("student-code") String studentCode) {
        return ResponseEntity.ok(userService.verifyStudent(studentCode));
    }

    @PostMapping("/assign-student/{studentCode}")
    public ResponseEntity<ApplicationResponseDTO> assignStudentToBatch(@PathVariable("studentCode") String studentCode, @Valid @RequestBody StudentBatchRequestDTO studentBatchRequestDTO) {
        return ResponseEntity.ok(userService.assignStudentToBatch(studentCode,studentBatchRequestDTO));
    }

    @GetMapping("/all-students")
    public ResponseEntity<List<AllUserDTO>> getAllStudents(UserRole userRole){
        return ResponseEntity.ok(userService.getAllStudents(UserRole.STUDENT));
    }

    @GetMapping("/all-students-by-code/{studentCode}")
    public ResponseEntity<List<AllUserDTO>>getAllStudentsByStudentCode(UserRole userRole,@PathVariable("studentCode") String studentCode){
        return ResponseEntity.ok(userService.getAllStudentsByStudentCode(UserRole.STUDENT, studentCode));
    }
    @GetMapping("/all-teachers")
    public ResponseEntity<List<AllUserDTO>> getAllTeachers(UserRole userRole){
        return ResponseEntity.ok(userService.getAllTeachers(UserRole.TEACHER));
    }

    @GetMapping("/all-teachers-by-code/{teacherCode}")
    public ResponseEntity<List<AllUserDTO>>getAllTeachersByTeacherCode(UserRole userRole,@PathVariable("teacherCode") String teacherCode){
        return ResponseEntity.ok(userService.getAllTeachersByTeacherCode(UserRole.TEACHER, teacherCode));
    }
}
