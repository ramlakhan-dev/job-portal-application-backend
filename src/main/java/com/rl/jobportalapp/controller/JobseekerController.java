package com.rl.jobportalapp.controller;

import com.rl.jobportalapp.dto.JobApplicationRequest;
import com.rl.jobportalapp.service.JobseekerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Jobseeker APIs",
        description = "Operations for jobseeker"
)
@RestController
@RequestMapping("/api/jobseeker")
public class JobseekerController {

    @Autowired
    private JobseekerService jobseekerService;

    @Operation(
            summary = "View jobs",
            description = "Jobseeker can view all active jobs"
    )
    @GetMapping("/jobs")
    public ResponseEntity<?> viewJobs() {
        return new ResponseEntity<>(jobseekerService.viewJobs(), HttpStatus.OK);
    }

    @Operation(
            summary = "Apply for job",
            description = "Jobseeker can apply for job"
    )
    @PostMapping("/apply")
    public ResponseEntity<?> apply(
            @RequestBody JobApplicationRequest jobApplicationRequest,
            Authentication authentication
    ) {
        String email = authentication.getName();
        return new ResponseEntity<>(jobseekerService.apply(jobApplicationRequest, email), HttpStatus.CREATED);
    }

    @Operation(
            summary = "View own job applications",
            description = "Jobseeker can view their own job applications"
    )
    @GetMapping("/applications")
    public ResponseEntity<?> myApplication(
            Authentication authentication
    ) {
        String email = authentication.getName();
        return new ResponseEntity<>(jobseekerService.myApplication(email), HttpStatus.OK);
    }
}
