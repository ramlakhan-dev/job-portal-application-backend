package com.rl.jobportalapp.repository;

import com.rl.jobportalapp.entity.Job;
import com.rl.jobportalapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findByRecruiter(User recruiter);
    Optional<Job> findByIdAndRecruiter(Long jobId, User recruiter);
}
