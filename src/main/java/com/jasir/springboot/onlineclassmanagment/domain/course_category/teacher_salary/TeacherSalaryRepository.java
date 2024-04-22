package com.jasir.springboot.onlineclassmanagment.domain.course_category.teacher_salary;

import com.jasir.springboot.onlineclassmanagment.domain.course_category.Months;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherSalaryRepository extends JpaRepository<TeacherSalary,Long> {
    Optional<TeacherSalary> findByPaidMonthAndTeacherIdAndYear(Months months,Long teacherId,String year);

    @Query("SELECT new com.jasir.springboot.onlineclassmanagment.domain.course_category.teacher_salary.TeacherSalaryDTO(" +
            "t.code,t.name,ts.payment,ts.paidDate,ts.year,ts.paidMonth,ts.paymentType" +
            ") FROM TeacherSalary ts JOIN ts.teacher t")
    List<TeacherSalaryDTO> findTeacherSalaries();
    @Query("SELECT new com.jasir.springboot.onlineclassmanagment.domain.course_category.teacher_salary.TeacherSalaryDTO(" +
            "t.code,t.name,ts.payment,ts.paidDate,ts.year,ts.paidMonth,ts.paymentType" +
            ") FROM TeacherSalary ts JOIN ts.teacher t WHERE t.code LIKE %:teacherCode%")
    List<TeacherSalaryDTO> findTeacherSalariesByTeacherCode(@Param("teacherCode") String teacherCode);
}
