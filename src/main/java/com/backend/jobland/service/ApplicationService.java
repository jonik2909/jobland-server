package com.backend.jobland.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.backend.jobland.dto.ApplicationDto;
import com.backend.jobland.dto.CompanyDto.CompanyApplicationUpdateStatus;
import com.backend.jobland.dto.CompanyDto.CompanyApplicationsInquiry;
import com.backend.jobland.entity.Application;
import com.backend.jobland.entity.Job;
import com.backend.jobland.lib.AppErrors;
import com.backend.jobland.lib.enums.ApplicationStatus;
import com.backend.jobland.repository.ApplicationRepository;
import com.backend.jobland.security.SecurityUtils;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final JobService jobService;
    private final SecurityUtils securityUtils;

    @Transactional
    public Application submitApplication(ApplicationDto.ApplicationSubmit data) {
        String candidateId = securityUtils.getCurrentUser().getId();
        String jobId = data.getJobId();

        Job job = jobService.findJobById(jobId);

        boolean exists = applicationRepository.existsByCandidateIdAndJobId(candidateId, jobId);

        if (exists) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, AppErrors.ALREADY_APPLIED);
        }

        try {
            Application app = new Application();
            app.setJobId(jobId);
            app.setCompanyId(job.getCompanyId());
            app.setCandidateId(candidateId);

            return applicationRepository.save(app);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, AppErrors.CREATE_FAILED);
        }
    }

    @Transactional
    public void deleteApplication(String id) {
        String candidateId = securityUtils.getCurrentUser().getId();
        Application app = applicationRepository.findByIdAndCandidateId(id, candidateId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, AppErrors.DATA_NOT_FOUND));

        if (app.getApplicationStatus() != ApplicationStatus.SUBMITTED
                && app.getApplicationStatus() != ApplicationStatus.VIEWED) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, AppErrors.CANNOT_DELETE_APPLICATION);
        }

        applicationRepository.delete(app);
    }

    public Map<String, Object> getMyApplications(ApplicationDto.ApplicationsInquiry query) {
        String candidateId = securityUtils.getCurrentUser().getId();

        int page = query.getPage();
        int limit = query.getLimit();

        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        PageRequest pageRequest = PageRequest.of(page - 1, limit, sort);

        ApplicationStatus status = query.getApplicationStatus();

        Page<Application> applicationPage = applicationRepository.findApplicationsByFilters(
                candidateId,
                status,
                pageRequest);

        Map<String, Object> response = new HashMap<>();
        response.put("list", applicationPage.getContent());
        response.put("total", applicationPage.getTotalElements());
        return response;
    }

    public Optional<Application> findByCandidateIdAndJobId(String candidateId, String jobId) {
        return applicationRepository.findByCandidateIdAndJobId(candidateId, jobId);
    }

    /** COMPANY **/
    public Map<String, Object> getCompanyApplications(CompanyApplicationsInquiry query) {
        String companyId = securityUtils.getCurrentUser().getId();

        int page = query.getPage();
        int limit = query.getLimit();

        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        PageRequest pageRequest = PageRequest.of(page - 1, limit, sort);

        String jobId = query.getJobId();
        ApplicationStatus status = query.getApplicationStatus();

        Page<Application> applicationPage = applicationRepository.findCompanyApplications(companyId, jobId, status,
                pageRequest);

        Map<String, Object> stats = new HashMap<>();
        stats.put("applied", applicationRepository.countByJobId(jobId));
        stats.put("approved",
                applicationRepository.countByJobIdAndApplicationStatus(jobId, ApplicationStatus.APPROVED));
        stats.put("rejected",
                applicationRepository.countByJobIdAndApplicationStatus(jobId, ApplicationStatus.REJECTED));

        Map<String, Object> response = new HashMap<>();
        response.put("list", applicationPage.getContent());
        response.put("total", applicationPage.getTotalElements());
        response.put("stats", stats);
        return response;
    }

    @Transactional
    public Application updateApplicationStatus(String id, CompanyApplicationUpdateStatus data) {
        String companyId = securityUtils.getCurrentUser().getId();
        Application app = applicationRepository.findByIdAndCompanyId(id, companyId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, AppErrors.DATA_NOT_FOUND));

        app.setApplicationStatus(data.getApplicationStatus());
        return applicationRepository.save(app);
    }
}
