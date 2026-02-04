package com.rl.jobportalapp.controller;

import com.rl.jobportalapp.dto.JobRequest;
import com.rl.jobportalapp.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recruiter/jobs")
public class JobController {

    @Autowired
    private JobService jobService;


    @PostMapping
    public ResponseEntity<?> createJob(
            @RequestBody JobRequest jobRequest,
            Authentication authentication
    ) {
        String email = authentication.getName();
        return new ResponseEntity<>(jobService.crateJob(jobRequest, email), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getJobs(
            Authentication authentication
    ) {
        String email = authentication.getName();
        return new ResponseEntity<>(jobService.getJobs(email), HttpStatus.OK);
    }

    @PutMapping("/{jobId}")
    public ResponseEntity<?> updateJob(
            @PathVariable Long jobId,
            @RequestBody JobRequest jobRequest,
            Authentication authentication
    ) {
        String email = authentication.getName();
        return new ResponseEntity<>(jobService.updateJob(jobId, jobRequest, email), HttpStatus.OK);
    }

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
