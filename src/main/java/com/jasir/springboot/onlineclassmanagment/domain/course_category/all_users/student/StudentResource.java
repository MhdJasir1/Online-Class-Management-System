package com.jasir.springboot.onlineclassmanagment.domain.course_category.all_users.student;

import com.jasir.springboot.onlineclassmanagment.domain.course_category.all_users.SelectCourseDTO;
import com.jasir.springboot.onlineclassmanagment.dto.ApplicationResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/student" ,produces = MediaType.APPLICATION_JSON_VALUE)
public class StudentResource {
    private final StudentService studentService;

    @PostMapping("/update-profile")
    public ResponseEntity<ApplicationResponseDTO> updateProfile(@Valid @RequestBody StudentUpdateDTO studentUpdateDTO) {
        return ResponseEntity.ok(studentService.updateProfile(studentUpdateDTO));
    }

    @PostMapping("/select-course")
    public ResponseEntity<ApplicationResponseDTO> selectCourse(@Valid @RequestBody SelectCourseDTO selectCourseDTO) {
        return ResponseEntity.ok(studentService.selectCourse(selectCourseDTO));
    }

}
