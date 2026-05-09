package com.backend.jobland.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.backend.jobland.dto.AdminDto;
import com.backend.jobland.dto.MemberDto;
import com.backend.jobland.entity.Member;
import com.backend.jobland.lib.AppErrors;
import com.backend.jobland.lib.AppUtils;
import com.backend.jobland.lib.enums.MemberSort;
import com.backend.jobland.lib.enums.MemberStatus;
import com.backend.jobland.lib.enums.MemberType;
import com.backend.jobland.lib.enums.ViewGroup;
import com.backend.jobland.repository.MemberRepository;
import com.backend.jobland.security.SecurityUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecurityUtils securityUtils;
    private final ViewService viewService;

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

    public Member checkMe() {
        String memberId = securityUtils.getCurrentUser().getId();

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, AppErrors.DATA_NOT_FOUND));

        if (member.getMemberStatus() != MemberStatus.ACTIVE) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, AppErrors.USER_BLOCKED);
        }

        return member;
    }

    public Member getMember(String targetId) {
        Member targetMember = memberRepository.findById(targetId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, AppErrors.DATA_NOT_FOUND));

        if (targetMember.getMemberStatus() != MemberStatus.ACTIVE) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, AppErrors.DATA_NOT_FOUND);
        }

        if (securityUtils.isLoggedIn()) {
            String memberId = securityUtils.getCurrentUser().getId();
            boolean wasRecorded = viewService.recordView(memberId, targetId, ViewGroup.MEMBER);
            if (wasRecorded) {
                targetMember.setMemberViews(targetMember.getMemberViews() + 1);
            }
        }

        // TODO: CANDIDATE BACKGROUND

        return targetMember;
    }

    @Transactional
    public void updateMemberViews(String memberId) {
        memberRepository.incrementMemberViews(memberId);
    }

    public Map<String, Object> getMembers(MemberDto.MembersInquiry query) {
        int page = query.getPage();
        int limit = query.getLimit();

        MemberSort sortParam = query.getSort() != null ? query.getSort() : MemberSort.createdAt;

        Sort sort = MemberSort.memberViews.equals(sortParam) ? Sort.by(Sort.Direction.DESC, "memberViews")
                : Sort.by(Sort.Direction.DESC, "createdAt");

        PageRequest pageRequest = PageRequest.of(page - 1, limit, sort);

        Page<Member> memberList = memberRepository.findMembersByFilters(
                query.getMemberType(),
                query.getMemberCategory(),
                query.getSearch(),
                query.getMemberFeatured(),
                null,
                false,
                pageRequest);

        Map<String, Object> response = new HashMap<>();
        response.put("list", memberList.getContent());
        response.put("total", memberList.getTotalElements());

        return response;
    }

    public Member updateMember(MemberDto.UpdateMember data) {
        String memberId = securityUtils.getCurrentUser().getId();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, AppErrors.DATA_NOT_FOUND));

        if (member.getMemberStatus() != MemberStatus.ACTIVE) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, AppErrors.USER_BLOCKED);
        }

        AppUtils.copyNonNulls(data, member);

        return memberRepository.save(member);
    }

    /** ADMIN **/
    public Map<String, Object> getMembersByAdmin(AdminDto.AdminMembersInquiry query) {
        int page = query.getPage();
        int limit = query.getLimit();

        MemberSort sortParam = query.getSort() != null ? query.getSort() : MemberSort.createdAt;

        Sort sort = MemberSort.memberViews.equals(sortParam) ? Sort.by(Sort.Direction.DESC, "memberViews")
                : Sort.by(Sort.Direction.DESC, "createdAt");

        PageRequest pageRequest = PageRequest.of(page - 1, limit, sort);

        Page<Member> memberList = memberRepository.findMembersByFilters(
                query.getMemberType(),
                query.getMemberCategory(),
                query.getSearch(),
                query.getMemberFeatured(),
                query.getMemberStatus(),
                true,
                pageRequest);

        Map<String, Object> response = new HashMap<>();
        response.put("list", memberList.getContent());
        response.put("total", memberList.getTotalElements());

        return response;
    }

    public Member updateMemberByAdmin(String memberId, AdminDto.AdminMemberUpdate data) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, AppErrors.DATA_NOT_FOUND));

        AppUtils.copyNonNulls(data, member);

        return memberRepository.save(member);

    }
}
