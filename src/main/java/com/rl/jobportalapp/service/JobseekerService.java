package com.rl.jobportalapp.service;

import com.rl.jobportalapp.dto.JobApplicationRequest;
import com.rl.jobportalapp.dto.JobApplicationResponse;
import com.rl.jobportalapp.dto.JobResponse;
import com.rl.jobportalapp.entity.Job;
import com.rl.jobportalapp.entity.JobApplication;
import com.rl.jobportalapp.entity.User;
import com.rl.jobportalapp.mapper.JobApplicationMapper;
import com.rl.jobportalapp.mapper.JobMapper;
import com.rl.jobportalapp.repository.JobApplicationRepository;
import com.rl.jobportalapp.repository.JobRepository;
import com.rl.jobportalapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobseekerService {

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobMapper jobMapper;

    @Autowired
    private JobApplicationMapper jobApplicationMapper;

    public List<JobResponse> viewJobs() {
        return jobRepository.findByIsActiveTrue()
                .stream()
                .map(jobMapper::toDto)
                .toList();
    }

    public JobApplicationResponse apply(JobApplicationRequest jobApplicationRequest, String email) {
        User jobseeker = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Job job = jobRepository.findById(jobApplicationRequest.getJobId())
                .orElseThrow(() -> new RuntimeException("Job not found"));


        jobApplicationRepository.findByJobseekerAndJob(jobseeker, job)
                .ifPresent( j -> {
                    throw new RuntimeException("You have already applied for this job");
                });

        JobApplication jobApplication = new JobApplication();
        jobApplication.setJobseeker(jobseeker);
        jobApplication.setJob(job);

        JobApplication savedJobApplication = jobApplicationRepository.save(jobApplication);
        return jobApplicationMapper.toDto(savedJobApplication);
    }

    public List<JobApplicationResponse> myApplication(String email) {
        User jobseeker = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return jobApplicationRepository.findByJobseeker(jobseeker)
                .stream()
                .map(jobApplicationMapper::toDto)
                .toList();
    }
}
