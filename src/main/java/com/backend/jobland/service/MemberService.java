package com.backend.jobland.service;

import org.springframework.stereotype.Service;

@Service
public class MemberService {

    public String getMeber() {
        return "getMember API";
    }

    public String login(Object body) {
        System.out.println("memberService login");
        System.out.println("memberRepository");
        return "login successfully";
    }

}
