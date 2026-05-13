package com.backend.jobland.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.jobland.entity.Background;

@Repository
public interface BackgroundRepository extends JpaRepository<Background, String> {

}
