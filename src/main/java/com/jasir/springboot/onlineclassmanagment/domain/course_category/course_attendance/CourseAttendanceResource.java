package com.jasir.springboot.onlineclassmanagment.domain.course_category.course_attendance;

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
@RequestMapping(value = "/course-attendance", produces = MediaType.APPLICATION_JSON_VALUE)
public class CourseAttendanceResource {

    private final CourseAttendanceService courseAttendanceService;

    @PostMapping("/add-course-attendance/{course-time-schedule-id}")
    public ResponseEntity<ApplicationResponseDTO> addCourseAttendance(@PathVariable(name = "course-time-schedule-id") Long courseTimeScheduleId, @Valid @RequestBody CourseAttendanceRequestDTO courseAttendanceRequestDTO){
        return ResponseEntity.ok(courseAttendanceService.addCourseAttendance(courseTimeScheduleId,courseAttendanceRequestDTO));
    }

    @PostMapping("/update-course-attendance/{course-attendance-id}")
    public ResponseEntity<ApplicationResponseDTO> updateCourseAttendance(@PathVariable(name = "course-attendance-id") Long courseAttendanceId, @Valid @RequestBody CourseAttendanceRequestDTO courseAttendanceRequestDTO){
        return ResponseEntity.ok(courseAttendanceService.updateCourseAttendance(courseAttendanceId,courseAttendanceRequestDTO));
    }

    @GetMapping("/all-course-attendance")
    public ResponseEntity<List<CourseAttendanceDTO>> getAllCourseAttendance(){
        return ResponseEntity.ok(courseAttendanceService.getAllCourseAttendance());
    }

    @GetMapping("/all-course-attendance-by-date/{date}")
    public ResponseEntity<List<CourseAttendanceDTO>>getAllCourseAttendanceByDate(@PathVariable("date") String date){
        return ResponseEntity.ok(courseAttendanceService.getAllCourseAttendanceByDate(date));
    }

    @GetMapping("/all-course-attendance-by-student-code/{studentCode}")
    public ResponseEntity<List<CourseAttendanceDTO>>getAllCourseAttendanceByStatus(@PathVariable("studentCode") String studentCode){
        return ResponseEntity.ok(courseAttendanceService.getAllCourseAttendanceByStudentCode(studentCode));
    }
}
