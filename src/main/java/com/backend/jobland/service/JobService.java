package com.backend.jobland.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.backend.jobland.dto.AdminDto;
import com.backend.jobland.dto.CompanyDto;
import com.backend.jobland.dto.JobDto;
import com.backend.jobland.entity.Job;
import com.backend.jobland.lib.AppErrors;
import com.backend.jobland.lib.AppUtils;
import com.backend.jobland.lib.enums.JobSort;
import com.backend.jobland.lib.enums.JobStatus;
import com.backend.jobland.lib.enums.ViewGroup;
import com.backend.jobland.repository.JobRepository;
import com.backend.jobland.security.SecurityUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobService {

    private final SecurityUtils securityUtils;
    private final JobRepository jobRepository;
    private final MemberService memberService;
    private final ViewService viewService;

    public Map<String, Object> getJobs(JobDto.JobsInquiry query) {
        int page = query.getPage();
        int limit = query.getLimit();

        JobSort sortParam = query.getSort() != null ? query.getSort() : JobSort.createdAt;

        Sort sort = JobSort.jobViews.equals(sortParam) ? Sort.by(Sort.Direction.DESC, "jobViews")
                : Sort.by(Sort.Direction.DESC, "createdAt");

        PageRequest pageRequest = PageRequest.of(page - 1, limit, sort);

        Page<Job> jobList = jobRepository.findJobsByFilters(
                "PUBLIC",
                null,
                query.getCompanyId(),
                query.getJobType(),
                null,
                query.getJobLevel(),
                query.getJobCountry(),
                query.getJobCategory(),
                query.getSearch(),
                pageRequest);

        Map<String, Object> response = new HashMap<>();
        response.put("list", jobList.getContent());
        response.put("total", jobList.getTotalElements());

        return response;
    }

    public Job getJob(String jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, AppErrors.DATA_NOT_FOUND));

        if (job.getJobStatus() != JobStatus.ACTIVE) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, AppErrors.DATA_NOT_FOUND);
        }

        if (securityUtils.isLoggedIn()) {
            String memberId = securityUtils.getCurrentUser().getId();
            boolean wasRecorded = viewService.recordView(memberId, jobId, ViewGroup.JOB);
            if (wasRecorded) {
                job.setJobViews(job.getJobViews() + 1);
            }

            // TODO: meApplied
        }

        return job;
    }

    @Transactional
    public void updateJobViews(String jobId) {
        jobRepository.incrementJobViews(jobId);
    }

    /** COMPANY **/
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

    public Map<String, Object> getMyJobs(CompanyDto.CompanyJobsInquiry query) {
        int page = query.getPage();
        int limit = query.getLimit();

        JobSort sortParam = query.getSort() != null ? query.getSort() : JobSort.createdAt;

        Sort sort = JobSort.jobViews.equals(sortParam) ? Sort.by(Sort.Direction.DESC, "jobViews")
                : Sort.by(Sort.Direction.DESC, "createdAt");

        PageRequest pageRequest = PageRequest.of(page - 1, limit, sort);

        String companyId = securityUtils.getCurrentUser().getId();

        Page<Job> jobList = jobRepository.findJobsByFilters(
                "COMPANY",
                companyId,
                null,
                query.getJobType(),
                query.getJobStatus(),
                query.getJobLevel(),
                query.getJobCountry(),
                query.getJobCategory(),
                query.getSearch(),
                pageRequest);

        Map<String, Object> response = new HashMap<>();
        response.put("list", jobList.getContent());
        response.put("total", jobList.getTotalElements());

        return response;
    }

    public Job getMyJob(String jobId) {
        String memberId = securityUtils.getCurrentUser().getId();
        Job job = jobRepository.findByIdAndCompanyId(jobId, memberId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, AppErrors.DATA_NOT_FOUND));

        // TODO: applied Count

        return job;
    }

    /** ADMIN **/
    public Map<String, Object> getJobsByAdmin(AdminDto.AdminJobsInquiry query) {
        int page = query.getPage();
        int limit = query.getLimit();

        JobSort sortParam = query.getSort() != null ? query.getSort() : JobSort.createdAt;

        Sort sort = JobSort.jobViews.equals(sortParam) ? Sort.by(Sort.Direction.DESC, "jobViews")
                : Sort.by(Sort.Direction.DESC, "createdAt");

        PageRequest pageRequest = PageRequest.of(page - 1, limit, sort);

        Page<Job> jobList = jobRepository.findJobsByFilters(
                "ADMIN",
                null,
                query.getCompanyId(),
                query.getJobType(),
                query.getJobStatus(),
                query.getJobLevel(),
                query.getJobCountry(),
                query.getJobCategory(),
                query.getSearch(),
                pageRequest);

        Map<String, Object> response = new HashMap<>();
        response.put("list", jobList.getContent());
        response.put("total", jobList.getTotalElements());

        return response;
    }

    @Transactional
    public Job updateJobByAdmin(String jobId, AdminDto.AdminJobUpdate data) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, AppErrors.DATA_NOT_FOUND));

        JobStatus oldStatus = job.getJobStatus();
        AppUtils.copyNonNulls(data, job);
        JobStatus newStatus = job.getJobStatus();

        if (oldStatus != newStatus) {
            if (newStatus == JobStatus.ACTIVE) {
                memberService.updateActiveJobsCount(job.getCompanyId(), 1);
            } else if (oldStatus == JobStatus.ACTIVE) {
                memberService.updateActiveJobsCount(job.getCompanyId(), -1);
            }
        }
        return jobRepository.save(job);
    }

}
