package com.jasir.springboot.onlineclassmanagment.domain.course_category.course_time_schedule;

import com.jasir.springboot.onlineclassmanagment.domain.course_category.batch.BatchWithTeacherDetailsDTO;
import com.jasir.springboot.onlineclassmanagment.domain.course_category.course.Course;
import com.jasir.springboot.onlineclassmanagment.domain.course_category.course.CourseRequestDTO;
import com.jasir.springboot.onlineclassmanagment.domain.course_category.course.CourseUpdateRequestDTO;
import com.jasir.springboot.onlineclassmanagment.dto.ApplicationResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/course-time",produces = MediaType.APPLICATION_JSON_VALUE)
public class CourseTimeScheduleResource {

    private final CourseTimeScheduleService courseTimeScheduleService;

    @PostMapping("/add-course-time")
    public ResponseEntity<ApplicationResponseDTO> addCourseTime(@Valid @RequestBody CourseTimeScheduleRequestDTO courseTimeScheduleRequestDTO){
        return ResponseEntity.ok(courseTimeScheduleService.addCourseTime(courseTimeScheduleRequestDTO));
    }

    @PostMapping("/update-course-time/{course-time-schedule-id}")
    public ResponseEntity<ApplicationResponseDTO> updateCourse(@PathVariable(name = "course-time-schedule-id") Long courseTimeScheduleId, @Valid @RequestBody CourseTimeScheduleUpdateRequestDTO courseTimeScheduleUpdateRequestDTO){
        return ResponseEntity.ok(courseTimeScheduleService.updateCourse(courseTimeScheduleId,courseTimeScheduleUpdateRequestDTO));
    }

//    @GetMapping("/all-course-times")
//    public ResponseEntity<List<CourseTimeSchedule>> getAllCourseTimes(){
//        return ResponseEntity.ok(courseTimeScheduleService.getAllCourseTimes());
//    }

    @GetMapping("/all-course-times")
    public ResponseEntity<List<CourseTimeScheduleDTO>> getAllCourseTimes(){
        return ResponseEntity.ok(courseTimeScheduleService.getAllCourseTimes());
    }

}
