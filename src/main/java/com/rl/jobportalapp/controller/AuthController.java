package com.rl.jobportalapp.controller;

import com.rl.jobportalapp.dto.LoginRequest;
import com.rl.jobportalapp.dto.RefreshTokenRequest;
import com.rl.jobportalapp.dto.SignupRequest;
import com.rl.jobportalapp.enums.Role;
import com.rl.jobportalapp.service.AuthService;
import com.rl.jobportalapp.service.RefreshTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "Authentication APIs",
        description = "APIs for user signup and login with access and refresh tokens"
)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Operation(
            summary = "Signup Jobseeker",
            description = "Register a new jobseeker and generate access and refresh tokens"
    )
    @PostMapping("/signup/jobseeker")
    public ResponseEntity<?> signupJobseeker(
            @RequestBody SignupRequest signupRequest
    ) {
        return new ResponseEntity<>(authService.signup(signupRequest, Role.JOBSEEKER), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Signup Recruiter",
            description = "Register a new recruiter and generate access and refresh tokens"
    )
    @PostMapping("/signup/recruiter")
    public ResponseEntity<?> signupRecruiter(
            @RequestBody SignupRequest signupRequest
    ) {
        return new ResponseEntity<>(authService.signup(signupRequest, Role.RECRUITER), HttpStatus.CREATED);
    }

    @Operation(
            summary = "User Login",
            description = "Authenticate user and generate access and refresh tokens"
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequest loginRequest
    ) {
        return new ResponseEntity<>(authService.login(loginRequest), HttpStatus.OK);
    }

    @Operation(
            summary = "Regenerate access token",
            description = "Regenerate access token using refresh token"
    )
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(
            @RequestBody RefreshTokenRequest refreshTokenRequest
    ) {
        return new ResponseEntity<>(refreshTokenService.refresh(refreshTokenRequest), HttpStatus.OK);
    }
}
