package com.jasir.springboot.onlineclassmanagment.domain.course_category.course_time_schedule;

import com.jasir.springboot.onlineclassmanagment.domain.course_category.batch.BatchRepository;
import com.jasir.springboot.onlineclassmanagment.domain.course_category.batch.BatchWithTeacherDetailsDTO;
import com.jasir.springboot.onlineclassmanagment.domain.security.entity.UserRole;
import com.jasir.springboot.onlineclassmanagment.domain.security.repos.UserRepository;
import com.jasir.springboot.onlineclassmanagment.domain.security.service.AuthService;
import com.jasir.springboot.onlineclassmanagment.dto.ApplicationResponseDTO;
import com.jasir.springboot.onlineclassmanagment.exception.ApplicationCustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseTimeScheduleService {

    private final CourseTimeScheduleRepository courseTimeScheduleRepository;
    private final BatchRepository batchRepository;
    private final UserRepository userRepository;

    public ApplicationResponseDTO addCourseTime(CourseTimeScheduleRequestDTO courseTimeScheduleRequestDTO) {

        if (batchRepository.findByCode(courseTimeScheduleRequestDTO.getBatchCode()).isPresent()){
            //batch available
            Long batchId = batchRepository.findByCode(courseTimeScheduleRequestDTO.getBatchCode()).get().getBatchId();
            if (courseTimeScheduleRepository.findByBatchIdAndDateAndStartTime(batchId, courseTimeScheduleRequestDTO.getDate(), courseTimeScheduleRequestDTO.getStartTime()).isEmpty()){

                String currentUser = AuthService.getCurrentUser();

                if (userRepository.findByUsernameAndUserRoleAndStatus(currentUser, UserRole.ADMIN, 1).isPresent()) {
                    //real admin
                    courseTimeScheduleRepository.save(CourseTimeSchedule.builder()
                            .batchId(batchId)
                            .date(courseTimeScheduleRequestDTO.getDate())
                            .description(courseTimeScheduleRequestDTO.getDescription())
                            .endTime(courseTimeScheduleRequestDTO.getEndTime())
                            .scheduleType(TimeScheduleType.SCHEDULED)
                            .startTime(courseTimeScheduleRequestDTO.getStartTime())
                            .topic(courseTimeScheduleRequestDTO.getTopic())
                            .build());

                    return new ApplicationResponseDTO(HttpStatus.CREATED, "COURSE_TIME_SCHEDULE_CREATED", "Course time schedule created successfully");
                } else {
                    //not an admin
                    return new ApplicationResponseDTO(HttpStatus.UNAUTHORIZED, "ACCESS_DENIED", "Access denied");
                }
            }else{
                return new ApplicationResponseDTO(HttpStatus.BAD_REQUEST, "ALREADY_COURSE_SCHEDULED", "Already course scheduled");
            }

        }else{
            return new ApplicationResponseDTO(HttpStatus.BAD_REQUEST, "INVALID_BATCH", "Invalid batch");
        }
    }

    public ApplicationResponseDTO updateCourse(Long courseTimeScheduleId, CourseTimeScheduleUpdateRequestDTO courseTimeScheduleUpdateRequestDTO) {
        if (courseTimeScheduleRepository.findByCourseTimeScheduleId(courseTimeScheduleId).isPresent()) {

//            String oldName = courseRepository.findByCode(courseCode).get().getName();

            String currentUser = AuthService.getCurrentUser();

            if (userRepository.findByUsernameAndUserRoleAndStatus(currentUser, UserRole.ADMIN, 1).isPresent()) {
                //real admin
                CourseTimeSchedule courseTimeSchedule = courseTimeScheduleRepository.findByCourseTimeScheduleId(courseTimeScheduleId).get();
                courseTimeSchedule.setDate(courseTimeScheduleUpdateRequestDTO.getDate());
                courseTimeSchedule.setDescription(courseTimeScheduleUpdateRequestDTO.getDescription());
                courseTimeSchedule.setEndTime(courseTimeScheduleUpdateRequestDTO.getEndTime());
                courseTimeSchedule.setScheduleType(courseTimeScheduleUpdateRequestDTO.getTimeScheduleStatus());
                courseTimeSchedule.setStartTime(courseTimeScheduleUpdateRequestDTO.getStartTime());
                courseTimeSchedule.setTopic(courseTimeScheduleUpdateRequestDTO.getTopic());
                courseTimeScheduleRepository.save(courseTimeSchedule);

                return new ApplicationResponseDTO(HttpStatus.OK, "COURSE_TIME_SCHEDULE_UPDATED", "Course time schedule updated successfully");
            } else {
                //not an admin
                return new ApplicationResponseDTO(HttpStatus.UNAUTHORIZED, "ACCESS_DENIED", "Access denied");
            }

        } else {

            return new ApplicationResponseDTO(HttpStatus.BAD_REQUEST, "INVALID_COURSE_TIME_SCHEDULED", "Invalid course time scheduled");
        }
    }

    public List<CourseTimeScheduleDTO> getAllCourseTimes() {
        List<CourseTimeScheduleDTO> courseTimeSchedules = courseTimeScheduleRepository.findAllCourseTimeSchedules();
        return courseTimeSchedules;
    }

}
