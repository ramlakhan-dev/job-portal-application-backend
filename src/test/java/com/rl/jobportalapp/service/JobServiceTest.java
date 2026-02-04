package com.rl.jobportalapp.service;

import com.rl.jobportalapp.dto.JobRequest;
import com.rl.jobportalapp.dto.JobResponse;
import com.rl.jobportalapp.entity.Job;
import com.rl.jobportalapp.entity.User;
import com.rl.jobportalapp.mapper.JobMapper;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JobServiceTest {

    @Mock
    private JobRepository jobRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JobMapper jobMapper;

    @InjectMocks
    private JobService jobService;


    private User recruiter;
    private Job job;
    private JobResponse jobResponse;

    @BeforeEach
    void setup() {
        recruiter = new User();
        recruiter.setEmail("test@gmail.com");


        job = new Job();
        job.setId(1L);
        job.setTitle("title");
        job.setDescription("desc");
        job.setRecruiter(recruiter);

        jobResponse = new JobResponse();
        jobResponse.setId(1L);
        jobResponse.setTitle("title");
        jobResponse.setDescription("desc");
    }

    @Test
    void createJobShouldCreateJob() {
        JobRequest jobRequest = new JobRequest();
        jobRequest.setTitle("title");
        jobRequest.setDescription("desc");

        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(recruiter));

        when(jobRepository.save(any(Job.class)))
                .thenReturn(job);

        when(jobMapper.toEntity(jobRequest, recruiter))
                .thenReturn(job);

        when(jobMapper.toDto(job))
                .thenReturn(jobResponse);

        JobResponse jobRes = jobService.crateJob(jobRequest, "test@gmail.com");

        Assertions.assertNotNull(jobRes);
        Assertions.assertEquals("title", jobRes.getTitle());

        verify(jobRepository).save(any(Job.class));
    }

    @Test
    void getJobsShouldReturnJobs() {
        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(recruiter));

        when(jobRepository.findByRecruiter(recruiter))
                .thenReturn(List.of(job));

        when(jobMapper.toDto(job))
                .thenReturn(jobResponse);

        List<JobResponse> jobResponses = jobService.getJobs("test@gmail.com");

        Assertions.assertEquals(1, jobResponses.size());
        Assertions.assertEquals("title", jobResponses.getFirst().getTitle());

        verify(jobRepository).findByRecruiter(recruiter);
    }

    @Test
    void updateJobShouldUpdateJob() {

        JobRequest jobRequest = new JobRequest();
        jobRequest.setTitle("Title");
        jobRequest.setDescription("desc");

        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(recruiter));

        when(jobRepository.findByIdAndRecruiter(1L, recruiter))
                .thenReturn(Optional.of(job));

        doNothing().when(jobMapper).updateJob(job, jobRequest);

        when(jobRepository.save(any(Job.class)))
                .thenReturn(job);

        when(jobMapper.toDto(job))
                .thenReturn(jobResponse);

        JobResponse jobRes = jobService.updateJob(1L, jobRequest, "test@gmail.com");

        Assertions.assertNotNull(jobRes);
        verify(jobRepository).save(job);
        verify(jobMapper).toDto(job);
    }

    @Test
    void deleteJobShouldDeleteJob() {

        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(recruiter));

        when(jobRepository.findByIdAndRecruiter(1L, recruiter))
                .thenReturn(Optional.of(job));

        jobService.deleteJob(1L, "test@gmail.com");

        verify(jobRepository).save(job);
    }
}
