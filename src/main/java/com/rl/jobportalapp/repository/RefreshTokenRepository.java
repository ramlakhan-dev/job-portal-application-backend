package com.rl.jobportalapp.repository;

import com.rl.jobportalapp.entity.RefreshToken;
import com.rl.jobportalapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    void deleteByUser(User user);
    Optional<RefreshToken> findByToken(String token);
}
