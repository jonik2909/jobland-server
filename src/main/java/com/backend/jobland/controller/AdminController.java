package com.backend.jobland.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @GetMapping("/member/list")
    public Object getMembersByAdmin() {
        return "getMembersByAdmin API";
    }

    @PostMapping("/member/update/{id}")
    public Object updateMemberByAdmin() {
        return "updateMemberByAdmin API";
    }
}
