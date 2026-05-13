package com.backend.jobland.service;

import org.springframework.stereotype.Service;

import com.backend.jobland.repository.BackgroundRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BackgroundService {
    private final BackgroundRepository backgroundRepository;
}
