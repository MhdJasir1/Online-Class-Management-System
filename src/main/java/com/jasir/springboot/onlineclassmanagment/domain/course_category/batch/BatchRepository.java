package com.jasir.springboot.onlineclassmanagment.domain.course_category.batch;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Long> {
    Optional<Batch> findByCode(String batchCode);

    Optional<Batch> findByCodeAndName(String batchCode, String batchName);

    Optional<Batch> findByCourseId(Long courseId);

    Optional<Batch> findByName(String name);

    @Query("SELECT new com.jasir.springboot.onlineclassmanagment.domain.course_category.batch.BatchWithTeacherDetailsDTO(" +
            "b.batchId, b.name, b.code, b.year, b.fee, b.status, c.code, c.name, t.code, t.name" +
            ") FROM Batch b JOIN b.teacher t JOIN b.course c WHERE b.status=1")
    List<BatchWithTeacherDetailsDTO> findBatchesWithTeacherDetails();
    @Query("SELECT new com.jasir.springboot.onlineclassmanagment.domain.course_category.batch.BatchWithTeacherDetailsDTO(" +
            "b.batchId, b.name, b.code, b.year, b.fee, b.status, c.code, c.name, t.code, t.name" +
            ") FROM Batch b JOIN b.teacher t JOIN b.course c WHERE b.name LIKE %:name% AND b.status=1")
    List<BatchWithTeacherDetailsDTO> findBatchesWithTeacherDetailsByName(@Param("name") String name);

}
