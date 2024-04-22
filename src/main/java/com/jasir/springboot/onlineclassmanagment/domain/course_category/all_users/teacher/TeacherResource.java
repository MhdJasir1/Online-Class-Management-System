package com.jasir.springboot.onlineclassmanagment.domain.course_category.all_users.teacher;

import com.jasir.springboot.onlineclassmanagment.domain.course_category.all_users.SelectCourseDTO;
import com.jasir.springboot.onlineclassmanagment.domain.course_category.all_users.student.StudentUpdateDTO;
import com.jasir.springboot.onlineclassmanagment.dto.ApplicationResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/teacher" ,produces = MediaType.APPLICATION_JSON_VALUE)
public class TeacherResource {
    private final TeacherService teacherService;

    @PostMapping("/update-profile")
    public ResponseEntity<ApplicationResponseDTO> updateProfile(@Valid @RequestBody TeacherUpdateDTO teacherUpdateDTO) {
        return ResponseEntity.ok(teacherService.updateProfile(teacherUpdateDTO));
    }

    @PostMapping("/select-course")
    public ResponseEntity<ApplicationResponseDTO> selectCourse(@Valid @RequestBody SelectCourseDTO selectCourseDTO) {
        return ResponseEntity.ok(teacherService.selectCourse(selectCourseDTO));
    }

}
