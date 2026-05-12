package com.backend.jobland.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.jobland.entity.Job;

public interface JobRepository extends JpaRepository<Job, String> {
    Optional<Job> findByIdAndCompanyId(String id, String companyId);
}
