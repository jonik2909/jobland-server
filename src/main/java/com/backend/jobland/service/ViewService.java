package com.backend.jobland.service;

import java.util.Optional;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.jobland.entity.View;
import com.backend.jobland.lib.enums.ViewGroup;
import com.backend.jobland.repository.ViewRepository;

@Service
public class ViewService {
    private final ViewRepository viewRepository;
    private final MemberService memberService;
    private final JobService jobService;

    public ViewService(ViewRepository viewRepository, @Lazy MemberService memberService, @Lazy JobService jobService) {
        this.viewRepository = viewRepository;
        this.memberService = memberService;
        this.jobService = jobService;
    }

    @Transactional
    public boolean recordView(String memberId, String viewRefId, ViewGroup viewGroup) {
        Optional<View> existView = viewRepository.findFirstByMemberIdAndViewRefId(memberId, viewRefId);
        if (!existView.isPresent()) {
            View newView = new View();
            newView.setMemberId(memberId);
            newView.setViewRefId(viewRefId);
            newView.setViewGroup(viewGroup);
            viewRepository.save(newView);

            switch (viewGroup) {
                case ViewGroup.MEMBER:
                    memberService.updateMemberViews(viewRefId); // DB DATA UPDATE
                    break;

                case ViewGroup.JOB:
                    jobService.updateJobViews(viewRefId); // DB DATA UPDATE
                    break;

                default:
                    break;
            }
            return true;
        }
        return false;

    }
}
