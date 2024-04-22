package com.jasir.springboot.onlineclassmanagment.domain.course_category.course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {
    Optional<Course> findByCodeOrName(String code,String name);
    Optional<Course> findByCodeAndName(String code,String name);
    Optional<Course> findByName(String name);
    Optional<Course> findByCode(String code);
    List<Course> findAllByStatus(Integer status);
    List<Course> findAllByStatusAndNameContains(Integer status, String name);
}
