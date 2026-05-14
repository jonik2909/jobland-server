package com.backend.jobland.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.jobland.entity.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, String> {

}
