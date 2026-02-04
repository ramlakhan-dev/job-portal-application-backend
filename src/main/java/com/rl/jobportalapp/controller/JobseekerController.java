package com.rl.jobportalapp.controller;

import com.rl.jobportalapp.dto.JobApplicationRequest;
import com.rl.jobportalapp.service.JobseekerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jobseeker")
public class JobseekerController {

    @Autowired
    private JobseekerService jobseekerService;

    @GetMapping("/jobs")
    public ResponseEntity<?> viewJobs() {
        return new ResponseEntity<>(jobseekerService.viewJobs(), HttpStatus.OK);
    }

    @PostMapping("/apply")
    public ResponseEntity<?> apply(
            @RequestBody JobApplicationRequest jobApplicationRequest,
            Authentication authentication
    ) {
        String email = authentication.getName();
        return new ResponseEntity<>(jobseekerService.apply(jobApplicationRequest, email), HttpStatus.CREATED);
    }

    @GetMapping("/applications")
    public ResponseEntity<?> myApplication(
            Authentication authentication
    ) {
        String email = authentication.getName();
        return new ResponseEntity<>(jobseekerService.myApplication(email), HttpStatus.OK);
    }
}
