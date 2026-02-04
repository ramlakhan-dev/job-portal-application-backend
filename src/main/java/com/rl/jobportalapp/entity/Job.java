package com.rl.jobportalapp.entity;

import com.rl.jobportalapp.enums.JobType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "jobs")
@Data
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String title;

    @Column(nullable = false, length = 2000)
    @NotBlank
    private String description;

    @Column(nullable = false)
    @NotBlank
    private String location;

    private Double salary;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    private JobType jobType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruiter_id", nullable = false)
    private User recruiter;

    @Column(nullable = false)
    private boolean isActive = true;
}
