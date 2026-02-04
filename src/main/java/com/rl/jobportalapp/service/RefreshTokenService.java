package com.rl.jobportalapp.service;

import com.rl.jobportalapp.entity.RefreshToken;
import com.rl.jobportalapp.entity.User;
import com.rl.jobportalapp.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public RefreshToken createRefreshToken(User user) {
        refreshTokenRepository.deleteByUser(user);
        refreshTokenRepository.flush();

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(Instant.now().plus(Duration.ofDays(15)));

        return refreshTokenRepository.save(refreshToken);
    }
}
