package com.rl.jobportalapp.service;

import com.rl.jobportalapp.dto.AuthResponse;
import com.rl.jobportalapp.dto.SignupRequest;
import com.rl.jobportalapp.entity.RefreshToken;
import com.rl.jobportalapp.entity.User;
import com.rl.jobportalapp.enums.Role;
import com.rl.jobportalapp.repository.UserRepository;
import com.rl.jobportalapp.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthResponse signup(SignupRequest signupRequest, Role role) {
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new RuntimeException("User already exists");
        }

        User user = new User();
        user.setName(signupRequest.getName());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setRole(role);

        User savedUser = userRepository.save(user);

        String accessToken = jwtUtil.generateToken(savedUser);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(savedUser);

        return new AuthResponse(accessToken, refreshToken.getToken());
    }

}
