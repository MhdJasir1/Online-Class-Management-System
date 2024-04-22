package com.jasir.springboot.onlineclassmanagment.domain.course_category.course_attendance;

import com.jasir.springboot.onlineclassmanagment.domain.course_category.batch.BatchWithTeacherDetailsDTO;
import com.jasir.springboot.onlineclassmanagment.domain.course_category.course_time_schedule.CourseTimeScheduleRepository;
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
public class CourseAttendanceService {

    private final CourseAttendanceRepository courseAttendanceRepository;
    private final UserRepository userRepository;
    private final CourseTimeScheduleRepository courseTimeScheduleRepository;

    public ApplicationResponseDTO addCourseAttendance(Long courseTimeScheduleId, CourseAttendanceRequestDTO courseAttendanceRequestDTO) {

        if (userRepository.findByCodeAndUserRole(courseAttendanceRequestDTO.getStudentCode(),UserRole.STUDENT).isPresent()) {
            Long studentId = userRepository.findByCode(courseAttendanceRequestDTO.getStudentCode()).get().getUserId();

            if (courseTimeScheduleRepository.findById(courseTimeScheduleId).isPresent()) {

                if (courseAttendanceRepository.findByCourseTimeScheduleIdAndStudentId(courseTimeScheduleId, studentId).isEmpty()) {
                    String currentUser = AuthService.getCurrentUser();

                    if (userRepository.findByUsernameAndUserRoleAndStatus(currentUser, UserRole.ADMIN, 1).isPresent()) {
                        //real admin
                        CourseAttendance courseAttendance = new CourseAttendance();
                        courseAttendance.setCourseTimeScheduleId(courseTimeScheduleId);
                        courseAttendance.setAttendanceStatus(courseAttendanceRequestDTO.getAttendanceStatus());
                        courseAttendance.setStudentId(studentId);
                        courseAttendanceRepository.save(courseAttendance);
                        return new ApplicationResponseDTO(HttpStatus.CREATED, "COURSE_ATTENDANCE_MARKED", "Course attendance marked successfully");

                    } else {
                        //not an admin
                        return new ApplicationResponseDTO(HttpStatus.UNAUTHORIZED, "ACCESS_DENIED", "Access denied");
                    }
                } else {

                    return new ApplicationResponseDTO(HttpStatus.BAD_REQUEST, "ATTENDANCE_ALREADY_MARKED", "Attendance already marked");
                }

            } else {

                return new ApplicationResponseDTO(HttpStatus.BAD_REQUEST, "INVALID_COURSE_TIME_SCHEDULE", "Invalid course time schedule");
            }
        } else {
            return new ApplicationResponseDTO(HttpStatus.BAD_REQUEST, "INVALID_STUDENT", "Invalid student");
        }

    }

    public ApplicationResponseDTO updateCourseAttendance(Long courseAttendanceId, CourseAttendanceRequestDTO courseAttendanceRequestDTO) {

        if (userRepository.findByCodeAndUserRole(courseAttendanceRequestDTO.getStudentCode(),UserRole.STUDENT).isPresent()) {
            Long studentId = userRepository.findByCode(courseAttendanceRequestDTO.getStudentCode()).get().getUserId();

            if (courseAttendanceRepository.findById(courseAttendanceId).isPresent()) {

                    String currentUser = AuthService.getCurrentUser();

                    if (userRepository.findByUsernameAndUserRoleAndStatus(currentUser, UserRole.ADMIN, 1).isPresent()) {
                        //real admin
                        CourseAttendance courseAttendance = courseAttendanceRepository.findById(courseAttendanceId).get();
                        courseAttendance.setAttendanceStatus(courseAttendanceRequestDTO.getAttendanceStatus());
                        courseAttendance.setStudentId(studentId);
                        courseAttendanceRepository.save(courseAttendance);
                        return new ApplicationResponseDTO(HttpStatus.OK, "COURSE_ATTENDANCE_UPDATED", "Course attendance updated successfully");

                    } else {
                        //not an admin
                        return new ApplicationResponseDTO(HttpStatus.UNAUTHORIZED, "ACCESS_DENIED", "Access denied");
                    }

            } else {

                return new ApplicationResponseDTO(HttpStatus.BAD_REQUEST, "INVALID_COURSE_ATTENDANCE", "Invalid course attendance");
            }
        } else {
            return new ApplicationResponseDTO(HttpStatus.BAD_REQUEST, "INVALID_STUDENT", "Invalid student");
        }
    }

    public List<CourseAttendanceDTO> getAllCourseAttendance() {
        List<CourseAttendanceDTO> courseAttendanceDetails = courseAttendanceRepository.findCourseAttendanceDetails();
        return courseAttendanceDetails;
    }

    public List<CourseAttendanceDTO> getAllCourseAttendanceByDate(String date) {
        List<CourseAttendanceDTO> courseAttendanceDetails = courseAttendanceRepository.findCourseAttendanceDetailsByDate(date);
        return courseAttendanceDetails;
    }

    public List<CourseAttendanceDTO> getAllCourseAttendanceByStudentCode(String studentCode) {
        List<CourseAttendanceDTO> courseAttendanceDetails = courseAttendanceRepository.findCourseAttendanceDetailsByStudentCode(studentCode);
        return courseAttendanceDetails;
    }
}
