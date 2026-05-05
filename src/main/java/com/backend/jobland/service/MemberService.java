package com.backend.jobland.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.backend.jobland.dto.MemberDto;
import com.backend.jobland.entity.Member;
import com.backend.jobland.lib.AppErrors;
import com.backend.jobland.lib.enums.MemberStatus;
import com.backend.jobland.lib.enums.MemberType;
import com.backend.jobland.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member signup(MemberDto.Signup data) {
        if (data.getMemberType() == MemberType.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, AppErrors.ADMIN_SIGNUP_NOT_ALLOWED);
        }

        try {

            Member member = new Member();
            member.setMemberNick(data.getMemberNick().toLowerCase());
            member.setMemberPassword(passwordEncoder.encode(data.getMemberPassword()));
            member.setMemberPhone(data.getMemberPhone());
            member.setMemberType(data.getMemberType());

            return memberRepository.save(member); // DB CREATE
        } catch (Exception e) {
            System.out.println("Error: " + e);
            throw new ResponseStatusException(HttpStatus.CONFLICT, AppErrors.NICK_OR_PHONE_USED);
        }
    }

    public Member login(MemberDto.Login data) {
        String nick = data.getMemberNick().toLowerCase();

        Member member = memberRepository.findByMemberNick(nick)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, AppErrors.LOGIN_FAILED));

        boolean isMatch = passwordEncoder.matches(data.getMemberPassword(), member.getMemberPassword());

        if (!isMatch) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, AppErrors.LOGIN_FAILED);
        }

        if (member.getMemberStatus() != MemberStatus.ACTIVE) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, AppErrors.USER_BLOCKED);
        }

        return member;
    }

}
