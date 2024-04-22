package com.jasir.springboot.onlineclassmanagment.domain.course_category.all_users.student;

import com.jasir.springboot.onlineclassmanagment.domain.course_category.all_users.SelectCourseDTO;
import com.jasir.springboot.onlineclassmanagment.domain.course_category.all_users.student.student_course_temp.StudentCourse;
import com.jasir.springboot.onlineclassmanagment.domain.course_category.all_users.student.student_course_temp.StudentCourseRepository;
import com.jasir.springboot.onlineclassmanagment.domain.course_category.course.CourseRepository;
import com.jasir.springboot.onlineclassmanagment.domain.security.entity.User;
import com.jasir.springboot.onlineclassmanagment.domain.security.entity.UserRole;
import com.jasir.springboot.onlineclassmanagment.domain.security.repos.UserRepository;
import com.jasir.springboot.onlineclassmanagment.domain.security.service.AuthService;
import com.jasir.springboot.onlineclassmanagment.dto.ApplicationResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class StudentService {
    private final StudentCourseRepository studentCourseRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public ApplicationResponseDTO updateProfile(StudentUpdateDTO studentUpdateDTO) {
        String currentUser = AuthService.getCurrentUser();

        if (userRepository.findByUsernameAndUserRoleAndStatus(currentUser,UserRole.STUDENT,1).isPresent()){
            String mobile = userRepository.findByUsernameAndUserRoleAndStatus(currentUser, UserRole.STUDENT, 1).get().getMobile();
            String code = userRepository.findByUsernameAndUserRoleAndStatus(currentUser, UserRole.STUDENT, 1).get().getCode();
            //real admin
            if (userRepository.findByMobile(studentUpdateDTO.getMobile()).isEmpty() || mobile.equals(studentUpdateDTO.getMobile())){
                //student present
                User student = userRepository.findByCode(code).get();
                student.setName(studentUpdateDTO.getName());
                student.setMobile(studentUpdateDTO.getMobile());
                userRepository.save(student);

                return new ApplicationResponseDTO(HttpStatus.OK, "STUDENT_PROFILE_UPDATED", "Student profile updated successfully");
            }else{
                //student mobile found
                return new ApplicationResponseDTO(HttpStatus.NOT_FOUND, "MOBILE_ALREADY_EXIST", "Mobile already exist");
            }
        }else{
            //not an student
            return new ApplicationResponseDTO(HttpStatus.UNAUTHORIZED, "ACCESS_DENIED", "Access denied");
        }
    }

    public ApplicationResponseDTO selectCourse(SelectCourseDTO selectCourseDTO) {
        String currentUser = AuthService.getCurrentUser();

        if (courseRepository.findByCode(selectCourseDTO.getCourseCode()).isPresent()){
            Long courseId = courseRepository.findByCode(selectCourseDTO.getCourseCode()).get().getCourseId();
            if (userRepository.findByUsernameAndUserRoleAndStatus(currentUser,UserRole.STUDENT,1).isPresent()){

                Long studentId = userRepository.findByUsernameAndUserRoleAndStatus(currentUser, UserRole.STUDENT, 1).get().getUserId();
                //real student
                if (studentCourseRepository.findByStudentIdAndCourseId(studentId,courseId).isEmpty()){
                    //student present
                    StudentCourse studentCourse = new StudentCourse();
                    studentCourse.setCourseId(courseId);
                    studentCourse.setStudentId(studentId);
                    studentCourseRepository.save(studentCourse);

                    return new ApplicationResponseDTO(HttpStatus.CREATED, "COURSE_SELECTED", "Course selected successfully");
                }else{
                    //course already selected
                    return new ApplicationResponseDTO(HttpStatus.NOT_FOUND, "COURSE_ALREADY_SELECTED", "Course already selected");
                }
            }else{
                //not an student
                return new ApplicationResponseDTO(HttpStatus.UNAUTHORIZED, "ACCESS_DENIED", "Access denied");
            }
        }else{
            return new ApplicationResponseDTO(HttpStatus.NOT_FOUND, "COURSE_NOT_FOUND", "Course not found");
        }

    }
}