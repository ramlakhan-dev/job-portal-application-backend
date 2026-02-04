package com.rl.jobportalapp.service;

import com.rl.jobportalapp.dto.JobRequest;
import com.rl.jobportalapp.dto.JobResponse;
import com.rl.jobportalapp.entity.Job;
import com.rl.jobportalapp.entity.User;
import com.rl.jobportalapp.mapper.JobMapper;
import com.rl.jobportalapp.repository.JobRepository;
import com.rl.jobportalapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobMapper jobMapper;

    public JobResponse crateJob(JobRequest jobRequest, String email) {
        User recruiter = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Job job = jobMapper.toEntity(jobRequest, recruiter);
        Job savedJob = jobRepository.save(job);

        return jobMapper.toDto(savedJob);
    }

    public List<JobResponse> getJobs(String email) {
        User recruiter = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return jobRepository.findByRecruiter(recruiter)
                .stream()
                .map(jobMapper::toDto)
                .toList();
    }

    public JobResponse updateJob(Long jobId, JobRequest jobRequest, String email) {
        User recruiter = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Job savedJob = jobRepository.findByIdAndRecruiter(jobId, recruiter)
                .orElseThrow(() -> new RuntimeException("Job not found or access denied"));

        jobMapper.updateJob(savedJob, jobRequest);
        Job updatedJob = jobRepository.save(savedJob);

        return jobMapper.toDto(updatedJob);
    }

    public void deleteJob(Long jobId, String email) {
        User recruiter = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Job savedJob = jobRepository.findByIdAndRecruiter(jobId, recruiter)
                .orElseThrow(() -> new RuntimeException("Job not found or access denied"));

        savedJob.setActive(false);
        jobRepository.save(savedJob);
    }
}
