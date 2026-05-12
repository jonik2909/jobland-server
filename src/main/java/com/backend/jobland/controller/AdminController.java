package com.backend.jobland.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.jobland.dto.AdminDto;
import com.backend.jobland.entity.Job;
import com.backend.jobland.entity.Member;
import com.backend.jobland.service.JobService;
import com.backend.jobland.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final MemberService memberService;
    private final JobService jobService;

    @GetMapping("/member/list")
    public ResponseEntity<Object> getMembersByAdmin(@Valid AdminDto.AdminMembersInquiry query) {
        Map<String, Object> result = memberService.getMembersByAdmin(query);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/member/update/{id}")
    public ResponseEntity<Member> updateMemberByAdmin(@PathVariable("id") String id,
            @Valid @RequestBody AdminDto.AdminMemberUpdate body) {
        Member result = memberService.updateMemberByAdmin(id, body);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/job/list")
    public ResponseEntity<Object> getJobsByAdmin(@Valid AdminDto.AdminJobsInquiry query) {
        Map<String, Object> result = jobService.getJobsByAdmin(query);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/job/update/{id}")
    public ResponseEntity<Job> updateJobByAdmin(
            @PathVariable("id") String id,
            @Valid @RequestBody AdminDto.AdminJobUpdate data) {
        Job result = jobService.updateJobByAdmin(id, data);
        return ResponseEntity.ok(result);
    }
}
