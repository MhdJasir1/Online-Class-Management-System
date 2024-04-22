package com.jasir.springboot.onlineclassmanagment.domain.course_category.all_users.teacher;

import com.jasir.springboot.onlineclassmanagment.domain.course_category.all_users.SelectCourseDTO;
import com.jasir.springboot.onlineclassmanagment.domain.course_category.all_users.student.student_course_temp.StudentCourse;
import com.jasir.springboot.onlineclassmanagment.domain.course_category.all_users.student.teacher_course_temp.TeacherCourse;
import com.jasir.springboot.onlineclassmanagment.domain.course_category.all_users.student.teacher_course_temp.TeacherCourseRepository;
import com.jasir.springboot.onlineclassmanagment.domain.course_category.course.CourseRepository;
import com.jasir.springboot.onlineclassmanagment.domain.security.entity.User;
import com.jasir.springboot.onlineclassmanagment.domain.security.entity.UserRole;
import com.jasir.springboot.onlineclassmanagment.domain.security.repos.UserRepository;
import com.jasir.springboot.onlineclassmanagment.domain.security.service.AuthService;
import com.jasir.springboot.onlineclassmanagment.dto.ApplicationResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class TeacherService {
    private final TeacherCourseRepository teacherCourseRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public ApplicationResponseDTO updateProfile(@Valid TeacherUpdateDTO teacherUpdateDTO) {
        String currentUser = AuthService.getCurrentUser();

        if (userRepository.findByUsernameAndUserRoleAndStatus(currentUser,UserRole.TEACHER,1).isPresent()){
            String mobile = userRepository.findByUsernameAndUserRoleAndStatus(currentUser, UserRole.TEACHER, 1).get().getMobile();
            String code = userRepository.findByUsernameAndUserRoleAndStatus(currentUser, UserRole.TEACHER, 1).get().getCode();
            //real admin
            if (userRepository.findByMobile(teacherUpdateDTO.getMobile()).isEmpty() || mobile.equals(teacherUpdateDTO.getMobile())){
                //student present
                User teacher = userRepository.findByCode(code).get();
                teacher.setName(teacherUpdateDTO.getName());
                teacher.setMobile(teacherUpdateDTO.getMobile());
                userRepository.save(teacher);

                return new ApplicationResponseDTO(HttpStatus.OK, "TEACHER_PROFILE_UPDATED", "Teacher profile updated successfully");
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
            if (userRepository.findByUsernameAndUserRoleAndStatus(currentUser,UserRole.TEACHER,1).isPresent()){

                Long teacherId = userRepository.findByUsernameAndUserRoleAndStatus(currentUser, UserRole.TEACHER, 1).get().getUserId();
                //real teacher
                if (teacherCourseRepository.findByTeacherIdAndCourseId(teacherId,courseId).isEmpty()){
                    //student present
                    TeacherCourse teacherCourse = new TeacherCourse();
                    teacherCourse.setCourseId(courseId);
                    teacherCourse.setTeacherId(teacherId);
                    teacherCourseRepository.save(teacherCourse);

                    return new ApplicationResponseDTO(HttpStatus.CREATED, "COURSE_SELECTED", "Course selected successfully");
                }else{
                    //course already selected
                    return new ApplicationResponseDTO(HttpStatus.NOT_FOUND, "COURSE_ALREADY_SELECTED", "Course already selected");
                }
            }else{
                //not an teacher
                return new ApplicationResponseDTO(HttpStatus.UNAUTHORIZED, "ACCESS_DENIED", "Access denied");
            }
        }else{
            return new ApplicationResponseDTO(HttpStatus.NOT_FOUND, "COURSE_NOT_FOUND", "Course not found");
        }
    }
}