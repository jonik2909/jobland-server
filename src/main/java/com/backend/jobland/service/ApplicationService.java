package com.backend.jobland.service;

import org.springframework.stereotype.Service;

import com.backend.jobland.repository.ApplicationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
}
