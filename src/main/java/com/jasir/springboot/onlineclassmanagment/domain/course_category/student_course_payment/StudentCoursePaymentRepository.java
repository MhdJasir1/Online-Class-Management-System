package com.jasir.springboot.onlineclassmanagment.domain.course_category.student_course_payment;

import com.jasir.springboot.onlineclassmanagment.domain.course_category.batch.BatchWithTeacherDetailsDTO;
import com.jasir.springboot.onlineclassmanagment.domain.course_category.course_time_schedule.CourseTimeScheduleDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentCoursePaymentRepository extends JpaRepository<StudentCoursePayment,Long> {
    Optional<StudentCoursePayment> findByBatchIdAndStudentId(Long batchId,Long studentId);
    @Query("SELECT new com.jasir.springboot.onlineclassmanagment.domain.course_category.student_course_payment.StudentCoursePaymentDTO(" +
            "scp.paidDate,scp.payment,scp.paymentType,st.code,st.name,b.code,b.name" +
            ") FROM StudentCoursePayment scp JOIN scp.batch b JOIN scp.student st")
    List<StudentCoursePaymentDTO> findStudentPayments();
    @Query("SELECT new com.jasir.springboot.onlineclassmanagment.domain.course_category.student_course_payment.StudentCoursePaymentDTO(" +
            "scp.paidDate,scp.payment,scp.paymentType,st.code,st.name,b.code,b.name" +
            ") FROM StudentCoursePayment scp JOIN scp.batch b JOIN scp.student st WHERE st.code LIKE %:studentCode%")
    List<StudentCoursePaymentDTO> findStudentPaymentsByStudentCode(@Param("studentCode") String studentCode);
}
