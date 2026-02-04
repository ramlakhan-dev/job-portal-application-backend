package com.rl.jobportalapp.repository;

import com.rl.jobportalapp.entity.Job;
import com.rl.jobportalapp.entity.JobApplication;
import com.rl.jobportalapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    Optional<JobApplication> findByJobseekerAndJob(User jobseeker, Job job);
    List<JobApplication> findByJobseeker(User jobseeker);
}
