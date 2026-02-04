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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JobseekerServiceTest {

    @Mock
    private JobApplicationRepository jobApplicationRepository;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JobApplicationMapper jobApplicationMapper;

    @Mock
    private JobMapper jobMapper;

    @InjectMocks
    private JobseekerService jobseekerService;

    private User jobseeker;
    private Job job;
    private JobResponse jobResponse;
    private JobApplication jobApplication;
    private JobApplicationResponse jobApplicationResponse;

    @BeforeEach
    void setup() {

        jobseeker = new User();
        jobseeker.setEmail("test@gmail.com");

        job = new Job();
        job.setId(1L);
        job.setTitle("title");
        job.setDescription("desc");

        jobResponse = new JobResponse();
        jobResponse.setId(1L);
        jobResponse.setTitle("title");
        jobResponse.setDescription("desc");


        jobApplication = new JobApplication();
        jobApplication.setId(1L);
        jobApplication.setJob(job);
        jobApplication.setJobseeker(jobseeker);

        jobApplicationResponse = new JobApplicationResponse();
        jobApplicationResponse.setId(1L);
        jobApplicationResponse.setJobTitle(job.getTitle());
    }

    @Test
    void viewJobsShouldReturnActiveJobs() {

        when(jobRepository.findByIsActiveTrue())
                .thenReturn(List.of(job));

        when(jobMapper.toDto(job))
                .thenReturn(jobResponse);

        List<JobResponse> jobResponses = jobseekerService.viewJobs();

        Assertions.assertNotNull(jobResponses);
        Assertions.assertEquals(1, jobResponses.size());
        Assertions.assertEquals("title", jobResponses.getFirst().getTitle());

        verify(jobRepository).findByIsActiveTrue();
    }

    @Test
    void applyShouldApplyForJob() {
        JobApplicationRequest jobApplicationRequest = new JobApplicationRequest();
        jobApplicationRequest.setJobId(1L);

        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(jobseeker));

        when(jobRepository.findById(1L))
                .thenReturn(Optional.of(job));

        when(jobApplicationRepository.save(any(JobApplication.class)))
                .thenReturn(jobApplication);

        when(jobApplicationRepository.findByJobseekerAndJob(jobseeker, job))
                .thenReturn(Optional.empty());

        when(jobApplicationMapper.toDto(jobApplication))
                .thenReturn(jobApplicationResponse);

        JobApplicationResponse jobApplicationRes = jobseekerService.apply(jobApplicationRequest, "test@gmail.com");

        Assertions.assertNotNull(jobApplicationRes);

        verify(jobApplicationRepository).save(any(JobApplication.class));
    }

    @Test
    void myApplicationShouldReturnMyApplications() {

        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(jobseeker));

        when(jobApplicationRepository.findByJobseeker(jobseeker))
                .thenReturn(List.of(jobApplication));

        when(jobApplicationMapper.toDto(jobApplication))
                .thenReturn(jobApplicationResponse);

        List<JobApplicationResponse> jobApplicationResponses = jobseekerService.myApplication("test@gmail.com");

        Assertions.assertNotNull(jobApplicationResponses);
        Assertions.assertEquals(1, jobApplicationResponses.size());

        verify(jobApplicationRepository).findByJobseeker(jobseeker);
    }
}
