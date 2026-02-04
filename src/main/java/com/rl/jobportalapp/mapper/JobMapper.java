package com.rl.jobportalapp.mapper;

import com.rl.jobportalapp.dto.JobRequest;
import com.rl.jobportalapp.dto.JobResponse;
import com.rl.jobportalapp.entity.Job;
import com.rl.jobportalapp.entity.User;
import org.springframework.stereotype.Component;

@Component
public class JobMapper {

    public JobResponse toDto(Job job) {
        return new JobResponse(
                job.getId(),
                job.getTitle(),
                job.getDescription(),
                job.getLocation(),
                job.getSalary(),
                job.getJobType(),
                job.isActive()
        );
    }

    public Job toEntity(JobRequest jobRequest, User recruiter) {
        Job job = new Job();
        job.setTitle(jobRequest.getTitle());
        job.setDescription(jobRequest.getDescription());
        job.setLocation(jobRequest.getLocation());
        job.setSalary(jobRequest.getSalary());
        job.setJobType(jobRequest.getJobType());
        job.setRecruiter(recruiter);

        return job;
    }

    public void updateJob(Job job, JobRequest jobRequest) {
        job.setTitle(jobRequest.getTitle());
        job.setDescription(jobRequest.getDescription());
        job.setLocation(jobRequest.getLocation());
        job.setSalary(jobRequest.getSalary());
        job.setJobType(jobRequest.getJobType());
    }
}
