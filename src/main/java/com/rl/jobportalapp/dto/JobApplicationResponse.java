package com.rl.jobportalapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobApplicationResponse {

    private Long id;
    private Long jobId;
    private String jobTitle;
    private LocalDateTime appliedAt;
}
