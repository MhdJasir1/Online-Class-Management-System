package com.jasir.springboot.onlineclassmanagment.domain.course_category.student_batch;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/student-batch",produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class StudentBatchResource {

}
