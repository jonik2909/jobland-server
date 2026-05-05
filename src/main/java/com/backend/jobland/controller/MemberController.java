package com.backend.jobland.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.jobland.dto.MemberDto;
import com.backend.jobland.entity.Member;
import com.backend.jobland.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {
    private final MemberService memberService; // DI

    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@Valid @RequestBody MemberDto.Signup body) {
        Member member = memberService.signup(body);

        Map<String, Object> result = Map.of("member", member);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody MemberDto.Login body) {
        return memberService.login(body);
    }

}
