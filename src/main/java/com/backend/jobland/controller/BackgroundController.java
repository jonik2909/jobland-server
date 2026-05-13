package com.backend.jobland.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.jobland.dto.BackgroundDto;
import com.backend.jobland.entity.Background;
import com.backend.jobland.service.BackgroundService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/background")
public class BackgroundController {
    private final BackgroundService backgroundService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public ResponseEntity<Background> createBackground(@Valid @RequestBody BackgroundDto.BackgroundCreate body) {
        Background result = backgroundService.createBackground(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/update/{id}")
    public ResponseEntity<Background> updateBackground(
            @PathVariable("id") String id,
            @Valid @RequestBody BackgroundDto.BackgroundUpdate body) {
        Background result = backgroundService.updateBackground(id, body);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/my")
    public ResponseEntity<Object> getMyBackgrounds() {
        Object result = backgroundService.getMyBackgrounds();
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/delete/{id}")
    public ResponseEntity<Object> deleteBackground(
            @PathVariable("id") String id) {
        Object result = backgroundService.deleteBackground(id);
        return ResponseEntity.ok(result);
    }
}
