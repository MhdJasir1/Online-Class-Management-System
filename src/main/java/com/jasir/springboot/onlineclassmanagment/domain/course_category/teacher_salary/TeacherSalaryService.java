package com.jasir.springboot.onlineclassmanagment.domain.course_category.teacher_salary;

import com.jasir.springboot.onlineclassmanagment.domain.course_category.Months;
import com.jasir.springboot.onlineclassmanagment.domain.course_category.batch.BatchWithTeacherDetailsDTO;
import com.jasir.springboot.onlineclassmanagment.domain.course_category.student_course_payment.StudentCoursePayment;
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
public class TeacherSalaryService {

    private final UserRepository userRepository;
    private final TeacherSalaryRepository teacherSalaryRepository;

    public ApplicationResponseDTO addTeacherSalary(String teacherCode, TeacherSalaryRequestDTO teacherSalaryRequestDTO) {
        if (userRepository.findByCodeAndUserRoleAndStatus(teacherCode, UserRole.TEACHER, 1).isPresent()) {

            Long teacherId = userRepository.findByCode(teacherCode).get().getUserId();

            if (teacherSalaryRepository.findByPaidMonthAndTeacherIdAndYear(teacherSalaryRequestDTO.getPaidMonth(),teacherId,teacherSalaryRequestDTO.getYear()).isEmpty()){
                String currentUser = AuthService.getCurrentUser();

                if (userRepository.findByUsernameAndUserRoleAndStatus(currentUser, UserRole.ADMIN, 1).isPresent()) {
                    //real admin
                    TeacherSalary teacherSalary = new TeacherSalary();
                    teacherSalary.setTeacherId(teacherId);
                    teacherSalary.setYear(teacherSalaryRequestDTO.getYear());
                    teacherSalary.setPaidDate(teacherSalaryRequestDTO.getPaidDate());
                    teacherSalary.setPaymentType(teacherSalaryRequestDTO.getPaymentType());
                    teacherSalary.setPayment(teacherSalaryRequestDTO.getPayment());
                    teacherSalary.setPaidMonth(teacherSalaryRequestDTO.getPaidMonth());
                    teacherSalaryRepository.save(teacherSalary);

                    return new ApplicationResponseDTO(HttpStatus.CREATED, "TEACHER_SALARY_ADDED_SUCCESSFUL", "Teacher salary added successfully");
                } else {
                    //not an admin
                    return new ApplicationResponseDTO(HttpStatus.UNAUTHORIZED, "ACCESS_DENIED", "Access denied");
                }
            }else{
                return new ApplicationResponseDTO(HttpStatus.BAD_REQUEST, "TEACHER_SALARY_ALREADY_EXIST", "Teacher salary already exist for the month");
            }

        } else {
            return new ApplicationResponseDTO(HttpStatus.BAD_REQUEST, "INVALID_TEACHER", "Invalid teacher");
        }
    }

    public ApplicationResponseDTO updateTeacherSalary(Long teacherSalaryId, TeacherSalaryRequestDTO teacherSalaryRequestDTO) {
        if (teacherSalaryRepository.findById(teacherSalaryId).isPresent()) {

            Long teacherId = teacherSalaryRepository.findById(teacherSalaryId).get().getTeacherId();

            if (teacherSalaryRepository.findByPaidMonthAndTeacherIdAndYear(teacherSalaryRequestDTO.getPaidMonth(),teacherId,teacherSalaryRequestDTO.getYear()).isEmpty()){

                String currentUser = AuthService.getCurrentUser();

                if (userRepository.findByUsernameAndUserRoleAndStatus(currentUser, UserRole.ADMIN, 1).isPresent()) {
                    //real admin
                    TeacherSalary teacherSalary = teacherSalaryRepository.findById(teacherSalaryId).get();
                    teacherSalary.setPaidDate(teacherSalaryRequestDTO.getPaidDate());
                    teacherSalary.setYear(teacherSalaryRequestDTO.getYear());
                    teacherSalary.setPaymentType(teacherSalaryRequestDTO.getPaymentType());
                    teacherSalary.setPayment(teacherSalaryRequestDTO.getPayment());
                    teacherSalary.setPaidMonth(teacherSalaryRequestDTO.getPaidMonth());
                    teacherSalaryRepository.save(teacherSalary);

                    return new ApplicationResponseDTO(HttpStatus.OK, "TEACHER_SALARY_UPDATED", "Teacher salary updated successfully");
                } else {
                    //not an admin
                    return new ApplicationResponseDTO(HttpStatus.UNAUTHORIZED, "ACCESS_DENIED", "Access denied");
                }

            }else{

                TeacherSalary teacherSalary = teacherSalaryRepository.findByPaidMonthAndTeacherIdAndYear(teacherSalaryRequestDTO.getPaidMonth(), teacherId, teacherSalaryRequestDTO.getYear()).get();
                String paidMonth = teacherSalary.getPaidMonth().toString();
                String year = teacherSalary.getYear();

                if (paidMonth.equals(teacherSalaryRequestDTO.getPaidMonth().toString())&&year.equals(teacherSalaryRequestDTO.getYear())){
                    return new ApplicationResponseDTO(HttpStatus.BAD_REQUEST, "TEACHER_SALARY_ALREADY_EXIST", "Teacher salary already exist for the month");

                }else{
                    String currentUser = AuthService.getCurrentUser();

                    if (userRepository.findByUsernameAndUserRoleAndStatus(currentUser, UserRole.ADMIN, 1).isPresent()) {
                        //real admin
                        TeacherSalary teacherSalary1 = teacherSalaryRepository.findById(teacherSalaryId).get();
                        teacherSalary1.setPaidDate(teacherSalaryRequestDTO.getPaidDate());
                        teacherSalary1.setYear(teacherSalaryRequestDTO.getYear());
                        teacherSalary1.setPaymentType(teacherSalaryRequestDTO.getPaymentType());
                        teacherSalary1.setPayment(teacherSalaryRequestDTO.getPayment());
                        teacherSalary1.setPaidMonth(teacherSalaryRequestDTO.getPaidMonth());
                        teacherSalaryRepository.save(teacherSalary1);

                        return new ApplicationResponseDTO(HttpStatus.OK, "TEACHER_SALARY_UPDATED", "Teacher salary updated successfully");
                    } else {
                        //not an admin
                        return new ApplicationResponseDTO(HttpStatus.UNAUTHORIZED, "ACCESS_DENIED", "Access denied");
                    }
                }

            }

        } else {
            return new ApplicationResponseDTO(HttpStatus.BAD_REQUEST, "INVALID_TEACHER_PAYMENT", "Invalid teacher payment");
        }
    }

    public ApplicationResponseDTO deleteTeacherSalary(Long teacherSalaryId) {
        if (teacherSalaryRepository.findById(teacherSalaryId).isPresent()){
            String currentUser = AuthService.getCurrentUser();

            if (userRepository.findByUsernameAndUserRoleAndStatus(currentUser, UserRole.ADMIN, 1).isPresent()) {
                //real admin
                TeacherSalary teacherSalary = teacherSalaryRepository.findById(teacherSalaryId).get();
                teacherSalaryRepository.delete(teacherSalary);

                return new ApplicationResponseDTO(HttpStatus.OK, "TEACHER_SALARY_DELETED", "Teacher salary deleted successfully");
            } else {
                //not an admin
                return new ApplicationResponseDTO(HttpStatus.UNAUTHORIZED, "ACCESS_DENIED", "Access denied");
            }
        }else{
            return new ApplicationResponseDTO(HttpStatus.BAD_REQUEST, "INVALID_TEACHER_SALARY", "Invalid teacher salary");
        }
    }

    public List<TeacherSalaryDTO> getAllPayments() {
        List<TeacherSalaryDTO> teacherSalaries = teacherSalaryRepository.findTeacherSalaries();
        return teacherSalaries;
    }

    public List<TeacherSalaryDTO> getAllPaymentsByTeacherCode(String teacherCode) {
        List<TeacherSalaryDTO> teacherSalaries = teacherSalaryRepository.findTeacherSalariesByTeacherCode(teacherCode);
        return teacherSalaries;
    }
}
