package com.backend.jobland.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.jobland.entity.Job;

public interface JobRepository extends JpaRepository<Job, String> {

}
