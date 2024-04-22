package com.jasir.springboot.onlineclassmanagment.domain.course_category.course;

import com.jasir.springboot.onlineclassmanagment.dto.ApplicationResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/course", produces = MediaType.APPLICATION_JSON_VALUE)
public class CourseResource {
    private final CourseService courseService;

    @PostMapping("/add-course")
    public ResponseEntity<ApplicationResponseDTO> addCourse(@Valid @RequestBody CourseRequestDTO courseRequestDTO){
        return ResponseEntity.ok(courseService.addCourse(courseRequestDTO));
    }

    @PostMapping("/update-course/{course-code}")
    public ResponseEntity<ApplicationResponseDTO> updateCourse(@PathVariable(name = "course-code") String courseCode, @Valid @RequestBody CourseUpdateRequestDTO courseUpdateRequestDTO){
        return ResponseEntity.ok(courseService.updateCourse(courseCode,courseUpdateRequestDTO));
    }

//    @DeleteMapping("/delete-course/{course-code}")
//    public ResponseEntity<ApplicationResponseDTO> deleteCourse(@PathVariable(name = "course-code") String courseCode){
//        return ResponseEntity.ok(courseService.deleteCourse(courseCode));
//    }

    @GetMapping("/all-courses")
    public ResponseEntity<List<Course>> getAllCourses(){
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/all-courses-by/{name}")
    public ResponseEntity<List<Course>>getAllCoursesByName(@PathVariable("name") String name){
        return ResponseEntity.ok(courseService.getAllCoursesByName(name));
    }
}
