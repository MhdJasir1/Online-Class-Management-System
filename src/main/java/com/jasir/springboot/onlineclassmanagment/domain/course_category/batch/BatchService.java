package com.jasir.springboot.onlineclassmanagment.domain.course_category.batch;

import com.jasir.springboot.onlineclassmanagment.domain.course_category.course.Course;
import com.jasir.springboot.onlineclassmanagment.domain.course_category.course.CourseRepository;
import com.jasir.springboot.onlineclassmanagment.domain.security.entity.User;
import com.jasir.springboot.onlineclassmanagment.domain.security.entity.UserRole;
import com.jasir.springboot.onlineclassmanagment.domain.security.repos.UserRepository;
import com.jasir.springboot.onlineclassmanagment.domain.security.service.AuthService;
import com.jasir.springboot.onlineclassmanagment.dto.ApplicationResponseDTO;
import com.jasir.springboot.onlineclassmanagment.exception.ApplicationCustomException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BatchService {
    private final BatchRepository batchRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public ApplicationResponseDTO addBatch(BatchRequestDTO batchRequestDTO) {
        if (batchRepository.findByCodeAndName(batchRequestDTO.getBatchCode(), batchRequestDTO.getName()).isPresent()
                || batchRepository.findByCode(batchRequestDTO.getBatchCode()).isPresent()
                || batchRepository.findByName(batchRequestDTO.getName()).isPresent()) {
            return new ApplicationResponseDTO(HttpStatus.BAD_REQUEST, "BATCH_ALREADY_EXIST", "Batch already exists");
        } else {
            String currentUser = AuthService.getCurrentUser();


            if (userRepository.findByUsernameAndUserRoleAndStatus(currentUser, UserRole.ADMIN, 1).isPresent()) {
                //real admin

                if (courseRepository.findByCode(batchRequestDTO.getCourseCode()).isPresent()) {
                    //course code available
                    Optional<Course> byCourseCode = courseRepository.findByCode(batchRequestDTO.getCourseCode());
                    Long courseId = byCourseCode.get().getCourseId();

                    if (userRepository.findByCodeAndUserRoleAndStatus(batchRequestDTO.getTeacherCode(), UserRole.TEACHER, 1).isPresent()) {
                        //teacher code available
                        Optional<User> byTeacherCode = userRepository.findByCodeAndUserRole(batchRequestDTO.getTeacherCode(), UserRole.TEACHER);
                        Long userId = byTeacherCode.get().getUserId();

                        batchRepository.save(Batch.builder()
                                .name(batchRequestDTO.getName())
                                .code(batchRequestDTO.getBatchCode())
                                .year((batchRequestDTO.getYear()))
                                .fee(batchRequestDTO.getFee())
                                .courseId(courseId)
                                .teacherId(userId)
                                        .status(1)
                                .build());

                        return new ApplicationResponseDTO(HttpStatus.CREATED, "BATCH_CREATED", "Batch created successfully");
                    } else {

                        return new ApplicationResponseDTO(HttpStatus.BAD_REQUEST, "INVALID_TEACHER", "Invalid teacher");
                    }
                } else {
                    return new ApplicationResponseDTO(HttpStatus.BAD_REQUEST, "INVALID_COURSE", "Invalid course");
                }

            } else {
                //not an admin
                return new ApplicationResponseDTO(HttpStatus.UNAUTHORIZED, "ACCESS_DENIED", "Access denied");
            }
        }
    }

    public ApplicationResponseDTO updateBatch(String batchCode, @Valid BatchUpdateRequestDTO batchRequestDTO) {
        if (batchRepository.findByCode(batchCode).isPresent()) {

            String oldName = batchRepository.findByCode(batchCode).get().getName();

            String currentUser = AuthService.getCurrentUser();

            if (userRepository.findByUsernameAndUserRoleAndStatus(currentUser, UserRole.ADMIN, 1).isPresent()) {
                //real admin
                if ((batchRepository.findByName(batchRequestDTO.getName()).isEmpty() || batchRepository.findByName(oldName).isEmpty())
                        || batchRepository.findByCodeAndName(batchCode, batchRequestDTO.getName()).isPresent()) {

                    if (userRepository.findByCodeAndUserRoleAndStatus(batchRequestDTO.getTeacherCode(), UserRole.TEACHER, 1).isPresent()) {

                        Long userId = userRepository.findByCodeAndUserRoleAndStatus(batchRequestDTO.getTeacherCode(), UserRole.TEACHER, 1).get().getUserId();

                        Batch batch = batchRepository.findByCode(batchCode).get();
                        batch.setName(batchRequestDTO.getName());
                        batch.setYear(batchRequestDTO.getYear());
                        batch.setFee(batchRequestDTO.getFee());
                        batch.setTeacherId(userId);
                        batch.setStatus(1);
                        batchRepository.save(batch);
                        return new ApplicationResponseDTO(HttpStatus.OK, "BATCH_UPDATED", "Batch updated successfully");
                    } else {
                        return new ApplicationResponseDTO(HttpStatus.BAD_REQUEST, "INVALID_TEACHER", "Invalid teacher");

                    }

                } else {
                    return new ApplicationResponseDTO(HttpStatus.BAD_REQUEST, "BATCH_ALREADY_EXIST", "Batch already exists");
                }

            } else {
                //not an admin
                return new ApplicationResponseDTO(HttpStatus.UNAUTHORIZED, "ACCESS_DENIED", "Access denied");
            }

        } else {

            return new ApplicationResponseDTO(HttpStatus.BAD_REQUEST, "INVALID_BATCH", "Invalid batch");
        }
    }

//    public ApplicationResponseDTO deleteBatch(String batchCode) {
//
//        if (batchRepository.findByCode(batchCode).isPresent()) {
//
//            String currentUser = AuthService.getCurrentUser();
//
//            if (userRepository.findByUsernameAndUserRoleAndStatus(currentUser, UserRole.ADMIN, 1).isPresent()) {
//                //real admin
//                Batch batch = batchRepository.findByCode(batchCode).get();
//                batch.setStatus(0);
//                batchRepository.save(batch);
//                return new ApplicationResponseDTO(HttpStatus.OK, "BATCH_DELETED", "Batch deleted successfully");
//
//            } else {
//                //not an admin
//                return new ApplicationResponseDTO(HttpStatus.UNAUTHORIZED, "ACCESS_DENIED", "Access denied");
//            }
//
//        } else {
//            return new ApplicationResponseDTO(HttpStatus.BAD_REQUEST, "INVALID_BATCH", "Invalid batch");
//        }
//    }

    public List<BatchWithTeacherDetailsDTO> getAllBatches() {
        List<BatchWithTeacherDetailsDTO> batches = batchRepository.findBatchesWithTeacherDetails();
        return batches;
    }

    public List<BatchWithTeacherDetailsDTO> getAllBatchesByName(String name) {
        List<BatchWithTeacherDetailsDTO> batches = batchRepository.findBatchesWithTeacherDetailsByName(name);
        return batches;
    }
}
