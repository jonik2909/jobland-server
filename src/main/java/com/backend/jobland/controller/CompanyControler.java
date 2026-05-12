package com.backend.jobland.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.jobland.dto.CompanyDto;
import com.backend.jobland.entity.Job;
import com.backend.jobland.service.JobService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/company")
@PreAuthorize("hasRole('COMPANY')")
public class CompanyControler {

    private final JobService jobService;

    @PostMapping("/job/create")
    public ResponseEntity<Object> createJob(@Valid @RequestBody CompanyDto.JobCreate body) {
        Job result = jobService.createJob(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PostMapping("/job/update/{id}")
    public ResponseEntity<Object> updateJob(
            @PathVariable("id") String id,
            @Valid @RequestBody CompanyDto.JobUpdate body) {
        Job result = jobService.updateJob(id, body);
        return ResponseEntity.ok(result);
    }

}
