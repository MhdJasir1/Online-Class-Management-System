package com.jasir.springboot.onlineclassmanagment.domain.security.repos;

import com.jasir.springboot.onlineclassmanagment.domain.course_category.all_users.AllUserDTO;
import com.jasir.springboot.onlineclassmanagment.domain.security.entity.User;
import com.jasir.springboot.onlineclassmanagment.domain.security.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsernameAndStatus(String username,Integer status);
    Optional<User> findByUsernameAndUserRoleAndStatus(String username, UserRole userRole,Integer status);
    Optional<User> findByCode(String code);
    Optional<User> findByCodeAndUserRole(String code,UserRole userRole);
    Optional<User> findByMobile(String mobile);
    Optional<User> findByCodeAndUserRoleAndStatus(String code,UserRole userRole,Integer status);
    List<User> findAll();
    Optional<User> findTopByOrderByUserIdDesc();
    Optional<User> findByMobileOrUsername(String mobile,String username);

    @Query("SELECT new com.jasir.springboot.onlineclassmanagment.domain.course_category.all_users.AllUserDTO(" +
            "u.code,u.name,u.mobile,u.gender,u.userRole,u.registeredDate,u.status" +
            ") FROM User u WHERE u.userRole=:student")
    List<AllUserDTO> findAllStudents(UserRole student);
    @Query("SELECT new com.jasir.springboot.onlineclassmanagment.domain.course_category.all_users.AllUserDTO(" +
            "u.code,u.name,u.mobile,u.gender,u.userRole,u.registeredDate,u.status" +
            ") FROM User u WHERE u.userRole=:student AND u.code LIKE %:studentCode%")
    List<AllUserDTO> findAllStudentsByStudentCode(UserRole student, @Param("studentCode") String studentCode);
    @Query("SELECT new com.jasir.springboot.onlineclassmanagment.domain.course_category.all_users.AllUserDTO(" +
            "u.code,u.name,u.mobile,u.gender,u.userRole,u.registeredDate,u.status" +
            ") FROM User u WHERE u.userRole=:teacher")
    List<AllUserDTO> findAllTeachers(UserRole teacher);
    @Query("SELECT new com.jasir.springboot.onlineclassmanagment.domain.course_category.all_users.AllUserDTO(" +
            "u.code,u.name,u.mobile,u.gender,u.userRole,u.registeredDate,u.status" +
            ") FROM User u WHERE u.userRole=:teacher AND u.code LIKE %:teacherCode%")
    List<AllUserDTO> findAllTeachersByTeacherCode(UserRole teacher,@Param("teacherCode") String teacherCode);
}
