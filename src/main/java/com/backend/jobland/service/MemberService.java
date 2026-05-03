package com.backend.jobland.service;

import org.springframework.stereotype.Service;

@Service
public class MemberService {

    public String signup() {
        return "signup api";
    }

    public String login(Object body) {
        return "login api";
    }

}
