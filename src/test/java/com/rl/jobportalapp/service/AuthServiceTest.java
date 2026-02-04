package com.rl.jobportalapp.service;

import com.rl.jobportalapp.dto.AuthResponse;
import com.rl.jobportalapp.dto.LoginRequest;
import com.rl.jobportalapp.dto.SignupRequest;
import com.rl.jobportalapp.entity.RefreshToken;
import com.rl.jobportalapp.entity.User;
import com.rl.jobportalapp.enums.Role;
import com.rl.jobportalapp.repository.UserRepository;
import com.rl.jobportalapp.util.JwtUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RefreshTokenService refreshTokenService;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    @Test
    void signupShouldCreateUser() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setName("Test");
        signupRequest.setEmail("test@gmail.com");
        signupRequest.setPassword("1234");

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken("refresh-token");

        when(userRepository.existsByEmail(signupRequest.getEmail()))
                .thenReturn(false);

        when(passwordEncoder.encode(signupRequest.getPassword()))
                .thenReturn("encodedPassword");

        when(refreshTokenService.createRefreshToken(any()))
                .thenReturn(refreshToken);

        when(jwtUtil.generateToken(any()))
                .thenReturn("access-token");

        AuthResponse authResponse = authService.signup(signupRequest, any(Role.class));

        Assertions.assertNotNull(authResponse);
        Assertions.assertEquals("access-token", authResponse.getAccessToken());
        Assertions.assertEquals("refresh-token", authResponse.getRefreshToken());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void loginShouldReturnToken() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@gmail.com");
        loginRequest.setPassword("1234");

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken("refresh-token");

        User user = new User();
        user.setEmail("test@gmail.com");

        Authentication authentication = mock(Authentication.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        when(userRepository.findByEmail("test@gmail.com"))
                .thenReturn(Optional.of(user));

        when(refreshTokenService.createRefreshToken(any()))
                .thenReturn(refreshToken);

        when(jwtUtil.generateToken(any()))
                .thenReturn("access-token");

        AuthResponse authResponse = authService.login(loginRequest);

        Assertions.assertNotNull(authResponse);
        Assertions.assertEquals("access-token", authResponse.getAccessToken());
        Assertions.assertEquals("refresh-token", authResponse.getRefreshToken());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }
}
