package com.rl.jobportalapp.dto;

import com.rl.jobportalapp.enums.JobType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobResponse {

    private Long id;
    private String title;
    private String description;
    private String location;
    private Double salary;
    private JobType jobType;
    private boolean isActive;
}
