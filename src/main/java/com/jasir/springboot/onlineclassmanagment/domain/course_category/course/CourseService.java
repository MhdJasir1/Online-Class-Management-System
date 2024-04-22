package com.jasir.springboot.onlineclassmanagment.domain.course_category.course;

import com.jasir.springboot.onlineclassmanagment.domain.course_category.batch.BatchRepository;
import com.jasir.springboot.onlineclassmanagment.domain.security.entity.UserRole;
import com.jasir.springboot.onlineclassmanagment.domain.security.repos.UserRepository;
import com.jasir.springboot.onlineclassmanagment.domain.security.service.AuthService;
import com.jasir.springboot.onlineclassmanagment.domain.security.service.UserService;
import com.jasir.springboot.onlineclassmanagment.dto.ApplicationResponseDTO;
import com.jasir.springboot.onlineclassmanagment.exception.ApplicationCustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    public ApplicationResponseDTO addCourse(CourseRequestDTO courseRequestDTO) {
        if (courseRepository.findByCodeAndName(courseRequestDTO.getCode(), courseRequestDTO.getName()).isPresent()
                || courseRepository.findByCode(courseRequestDTO.getCode()).isPresent()
                || courseRepository.findByName(courseRequestDTO.getName()).isPresent()) {
            return new ApplicationResponseDTO(HttpStatus.BAD_REQUEST, "COURSE_ALREADY_EXIST", "Course already exists");
        } else {
            String currentUser = AuthService.getCurrentUser();

            if (userRepository.findByUsernameAndUserRoleAndStatus(currentUser, UserRole.ADMIN, 1).isPresent()) {
                //real admin
                courseRepository.save(Course.builder()
                        .code(courseRequestDTO.getCode())
                        .content(courseRequestDTO.getContent())
                        .description((courseRequestDTO.getDescription()))
                        .duration(courseRequestDTO.getDuration())
                        .durationType(courseRequestDTO.getDurationType())
                        .name(courseRequestDTO.getName())
                        .status(1)
                        .build());

                return new ApplicationResponseDTO(HttpStatus.CREATED, "COURSE_CREATED", "Course created successfully");
            } else {
                //not an admin
                return new ApplicationResponseDTO(HttpStatus.UNAUTHORIZED, "ACCESS_DENIED", "Access denied");
            }
        }
    }

    public ApplicationResponseDTO updateCourse(String courseCode, CourseUpdateRequestDTO courseUpdateRequestDTO) {

        if (courseRepository.findByCode(courseCode).isPresent()) {

            String oldName = courseRepository.findByCode(courseCode).get().getName();

            String currentUser = AuthService.getCurrentUser();

            if (userRepository.findByUsernameAndUserRoleAndStatus(currentUser, UserRole.ADMIN, 1).isPresent()) {
                //real admin
                if ((courseRepository.findByName(courseUpdateRequestDTO.getName()).isEmpty() || courseRepository.findByName(oldName).isEmpty())
                        || courseRepository.findByCodeAndName(courseCode, courseUpdateRequestDTO.getName()).isPresent()) {
                    Course course = courseRepository.findByCode(courseCode).get();
                    course.setContent(courseUpdateRequestDTO.getContent());
                    course.setDescription(courseUpdateRequestDTO.getDescription());
                    course.setDuration(courseUpdateRequestDTO.getDuration());
                    course.setDurationType(courseUpdateRequestDTO.getDurationType());
                    course.setName(courseUpdateRequestDTO.getName());
                    course.setStatus(1);
                    courseRepository.save(course);
                    return new ApplicationResponseDTO(HttpStatus.OK, "COURSE_UPDATED", "Course updated successfully");

                } else {
                    return new ApplicationResponseDTO(HttpStatus.BAD_REQUEST, "COURSE_ALREADY_EXIST", "Course already exists");
                }

            } else {
                //not an admin
                return new ApplicationResponseDTO(HttpStatus.UNAUTHORIZED, "ACCESS_DENIED", "Access denied");
            }

        } else {

            return new ApplicationResponseDTO(HttpStatus.BAD_REQUEST, "INVALID_COURSE", "Invalid course");
        }
    }

//    public ApplicationResponseDTO deleteCourse(String courseCode) {
//        if (courseRepository.findByCode(courseCode).isPresent()) {
//
//            String currentUser = AuthService.getCurrentUser();
//
//            if (userRepository.findByUsernameAndUserRoleAndStatus(currentUser, UserRole.ADMIN, 1).isPresent()) {
//                //real admin
//                Course course = courseRepository.findByCode(courseCode).get();
//                course.setStatus(0);
//                courseRepository.save(course);
//                return new ApplicationResponseDTO(HttpStatus.OK, "COURSE_DELETED", "Course deleted successfully");
//
//            } else {
//                //not an admin
//                return new ApplicationResponseDTO(HttpStatus.UNAUTHORIZED, "ACCESS_DENIED", "Access denied");
//            }
//
//        } else {
//            return new ApplicationResponseDTO(HttpStatus.BAD_REQUEST, "INVALID_COURSE", "Invalid course");
//        }
//    }

    public List<Course> getAllCourses() {
        List<Course> courses = courseRepository.findAllByStatus(1);
        if (courses.isEmpty()) {
            throw new ApplicationCustomException(HttpStatus.NOT_FOUND, "COURSES_NOT_FOUND", "Courses not found");
        }
        return courses;
    }

    public List<Course> getAllCoursesByName(String name) {
        List<Course> courses = courseRepository.findAllByStatusAndNameContains(1,name);
        if (courses.isEmpty()) {
            throw new ApplicationCustomException(HttpStatus.NOT_FOUND, "COURSES_NOT_FOUND", "Courses not found");
        }
        return courses;
    }
}
