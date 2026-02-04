package com.rl.jobportalapp.dto;

import com.rl.jobportalapp.enums.JobType;
import lombok.Data;

@Data
public class JobRequest {

    private String title;
    private String description;
    private String location;
    private Double salary;
    private JobType jobType;
}
