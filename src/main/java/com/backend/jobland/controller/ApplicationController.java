package com.backend.jobland.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.jobland.dto.ApplicationDto;
import com.backend.jobland.service.ApplicationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/application")
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/submit")
    public ResponseEntity<Object> submitApplication(@Valid @RequestBody ApplicationDto.ApplicationSubmit data) {
        Object result = applicationService.submitApplication(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/delete/{id}")
    public ResponseEntity<Object> deleteApplication(@PathVariable("id") String id) {
        applicationService.deleteApplication(id);
        return ResponseEntity.ok(Map.of("success", true));
    }

    // getMyApplication
}
