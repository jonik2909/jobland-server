package com.backend.jobland.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.backend.jobland.dto.ApplicationDto;
import com.backend.jobland.entity.Application;
import com.backend.jobland.entity.Job;
import com.backend.jobland.lib.AppErrors;
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
}
