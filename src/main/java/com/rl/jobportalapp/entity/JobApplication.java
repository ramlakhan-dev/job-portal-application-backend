package com.rl.jobportalapp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "job_applications")
@Data
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User jobseeker;

    @ManyToOne
    private Job job;

    private LocalDateTime appliedAt = LocalDateTime.now();
}
