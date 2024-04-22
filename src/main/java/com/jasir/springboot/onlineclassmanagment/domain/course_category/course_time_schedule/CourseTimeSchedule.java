package com.jasir.springboot.onlineclassmanagment.domain.course_category.course_time_schedule;

import com.jasir.springboot.onlineclassmanagment.domain.course_category.batch.Batch;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "course_time_schedule")
public class CourseTimeSchedule {
    @Id
    @Column(name = "course_time_schedule_id",nullable = false,updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseTimeScheduleId;

    @Column(name = "start_time",length = 20)
    private String startTime;

    @Column(name = "end_time",length = 20)
    private String endTime;

    @Column(name = "date",length = 15)
    private String date;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TimeScheduleType scheduleType = TimeScheduleType.SCHEDULED;

    @Column(name = "description",columnDefinition = "text")
    private String description;

    @Column(name = "topic",length = 150)
    private String topic;

    @Column(name = "batch_id")
    private Long batchId;

    @ManyToOne
    @JoinColumn(name = "batch_id",referencedColumnName = "batch_id",insertable = false,updatable = false)
    private Batch batch;
}
