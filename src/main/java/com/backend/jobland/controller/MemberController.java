package com.backend.jobland.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.backend.jobland.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService; // DI

    @GetMapping("/member")
    public String getMember() {
        return memberService.getMeber();
    }

    @PostMapping("/member/login")
    public String login(@RequestBody Object body) {
        System.out.println("POST, /member/login");
        System.out.println(body);
        return memberService.login(body);
    }

}
