package com.backend.jobland.service;

import org.springframework.stereotype.Service;

@Service
public class MemberService {

    public String signup() {
        return "signup api";
    }

    public String login(Object body) {
        System.out.println("memberService login");
        System.out.println("memberRepository");
        return "login successfully";
    }

}
