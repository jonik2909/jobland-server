package com.backend.jobland.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.backend.jobland.dto.CompanyDto;
import com.backend.jobland.entity.Job;
import com.backend.jobland.lib.AppErrors;
import com.backend.jobland.lib.AppUtils;
import com.backend.jobland.repository.JobRepository;
import com.backend.jobland.security.SecurityUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobService {

    private final SecurityUtils securityUtils;
    private final JobRepository jobRepository;
    private final MemberService memberService;

    @Transactional
    public Job createJob(CompanyDto.JobCreate data) {
        try {
            Job job = new Job();
            AppUtils.copyNonNulls(data, job);

            String companyId = securityUtils.getCurrentUser().getId();
            job.setCompanyId(companyId);

            Job result = jobRepository.save(job);
            memberService.updateActiveJobsCount(companyId, 1);
            return result;
        } catch (Exception e) {
            System.out.println("ERROR, createJob: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, AppErrors.CREATE_FAILED);
        }
    }
}
