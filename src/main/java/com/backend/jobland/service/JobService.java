package com.backend.jobland.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.backend.jobland.dto.CompanyDto;
import com.backend.jobland.entity.Job;
import com.backend.jobland.lib.AppErrors;
import com.backend.jobland.lib.AppUtils;
import com.backend.jobland.lib.enums.JobStatus;
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

    @Transactional
    public Job updateJob(String jobId, CompanyDto.JobUpdate data) {
        System.out.println("jobId: " + jobId);
        System.out.println("data: " + data);

        // step-1: check data
        String memberId = securityUtils.getCurrentUser().getId();
        Job job = jobRepository.findByIdAndCompanyId(jobId, memberId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, AppErrors.DATA_NOT_FOUND));

        // step-2: remove null value
        JobStatus oldStatus = job.getJobStatus();
        AppUtils.copyNonNulls(data, job);
        JobStatus newStatus = job.getJobStatus();

        // step-3: update member active jobs
        if (oldStatus != newStatus) {
            if (newStatus == JobStatus.ACTIVE) {
                memberService.updateActiveJobsCount(memberId, 1);
            } else if (oldStatus == JobStatus.ACTIVE) {
                memberService.updateActiveJobsCount(memberId, -1);
            }
        }
        return jobRepository.save(job);
    }
}
