package com.rl.jobportalapp.mapper;

import com.rl.jobportalapp.dto.JobApplicationRequest;
import com.rl.jobportalapp.dto.JobApplicationResponse;
import com.rl.jobportalapp.entity.JobApplication;
import org.springframework.stereotype.Component;

@Component
public class JobApplicationMapper {

    public JobApplicationResponse toDto(JobApplication jobApplication) {
        return new JobApplicationResponse (
                jobApplication.getId(),
                jobApplication.getJob().getId(),
                jobApplication.getJob().getTitle(),
                jobApplication.getAppliedAt()
        );
    }
}
