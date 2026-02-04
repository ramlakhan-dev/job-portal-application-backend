package com.rl.jobportalapp.controller;

import com.rl.jobportalapp.dto.JobRequest;
import com.rl.jobportalapp.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Job APIs",
        description = "CRUD operations for jobs"
)
@RestController
@RequestMapping("/api/recruiter/jobs")
public class JobController {

    @Autowired
    private JobService jobService;


    @Operation(
            summary = "Create a job",
            description = "Recruiter can create their own job"
    )
    @PostMapping
    public ResponseEntity<?> createJob(
            @RequestBody JobRequest jobRequest,
            Authentication authentication
    ) {
        String email = authentication.getName();
        return new ResponseEntity<>(jobService.crateJob(jobRequest, email), HttpStatus.CREATED);
    }

    @Operation(
            summary = "View own jobs",
            description = "Recruiter can view their own jobs"
    )
    @GetMapping
    public ResponseEntity<?> getJobs(
            Authentication authentication
    ) {
        String email = authentication.getName();
        return new ResponseEntity<>(jobService.getJobs(email), HttpStatus.OK);
    }

    @Operation(
            summary = "Update a job",
            description = "Recruiter can update their own job"
    )
    @PutMapping("/{jobId}")
    public ResponseEntity<?> updateJob(
            @PathVariable Long jobId,
            @RequestBody JobRequest jobRequest,
            Authentication authentication
    ) {
        String email = authentication.getName();
        return new ResponseEntity<>(jobService.updateJob(jobId, jobRequest, email), HttpStatus.OK);
    }

    @Operation(
            summary = "Delete a job",
            description = "Recruiter can delete their own job"
    )
    @DeleteMapping("/{jobId}")
    public ResponseEntity<?> deleteJob(
            @PathVariable Long jobId,
            Authentication authentication
    ) {
        String email = authentication.getName();
        jobService.deleteJob(jobId, email);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
