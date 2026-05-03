package com.backend.jobland.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.jobland.dto.MemberDto;
import com.backend.jobland.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {
    private final MemberService memberService; // DI

    @PostMapping("/signup")
    public String signup() {
        System.out.println("POST, signup");
        return memberService.signup();
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody MemberDto.Login body) {
        System.out.println("POST, login");
        System.out.println(body);
        return memberService.login(body);
    }

}
