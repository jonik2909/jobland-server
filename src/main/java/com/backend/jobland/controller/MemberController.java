package com.backend.jobland.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.jobland.dto.MemberDto;
import com.backend.jobland.entity.Member;
import com.backend.jobland.service.AuthService;
import com.backend.jobland.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {
    private final MemberService memberService; // DI
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@Valid @RequestBody MemberDto.Signup body) {
        Member member = memberService.signup(body);

        String token = authService.createToken(member);

        Map<String, Object> result = Map.of("member", member, "token", token);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody MemberDto.Login body) {
        Member member = memberService.login(body);

        String token = authService.createToken(member);

        Map<String, Object> result = Map.of("member", member, "token", token);

        return ResponseEntity.ok().body(result);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/checkMe")
    public Object checkMe() {
        Member result = memberService.checkMe();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getMember(@PathVariable String id) {
        Member result = memberService.getMember(id);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/list")
    public ResponseEntity<Object> getMembers(@Valid MemberDto.MembersInquiry query) {
        Object result = memberService.getMembers(query);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/update")
    public ResponseEntity<Member> updateMember(@Valid @RequestBody MemberDto.UpdateMember body) {
        Member result = memberService.updateMember(body);
        return ResponseEntity.ok(result);
    }
}
