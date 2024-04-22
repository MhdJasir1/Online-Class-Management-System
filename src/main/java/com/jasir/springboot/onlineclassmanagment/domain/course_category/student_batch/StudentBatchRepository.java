package com.jasir.springboot.onlineclassmanagment.domain.course_category.student_batch;

import com.jasir.springboot.onlineclassmanagment.domain.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface StudentBatchRepository extends JpaRepository<User,Long> {

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO student_batch (student_id, batch_id) VALUES (:studentId, :batchId)", nativeQuery = true)
    void saveStudentBatch(Long studentId, Long batchId);

    @Query("SELECT new com.jasir.springboot.onlineclassmanagment.domain.course_category.student_batch.StudentBatchDTO(" +
            "st.code,b.code) FROM Batch b JOIN b.students st WHERE st.code LIKE :studentCode AND b.code LIKE :batchCode")
    List<StudentBatchDTO> findByStudentCodeAndBatchCode(@Param("studentCode") String studentCode, @Param("batchCode") String batchCode);
}
