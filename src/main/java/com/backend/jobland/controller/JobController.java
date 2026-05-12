package com.backend.jobland.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.jobland.dto.JobDto;
import com.backend.jobland.service.JobService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/job")
public class JobController {
    private final JobService jobService;

    @GetMapping("/list")
    public ResponseEntity<Object> getJobs(@Valid JobDto.JobsInquiry query) {
        Map<String, Object> result = jobService.getJobs(query);
        return ResponseEntity.ok(result);
    }
}
