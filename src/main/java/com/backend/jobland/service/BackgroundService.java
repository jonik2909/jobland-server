package com.backend.jobland.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.backend.jobland.dto.BackgroundDto;
import com.backend.jobland.entity.Background;
import com.backend.jobland.lib.AppErrors;
import com.backend.jobland.repository.BackgroundRepository;
import com.backend.jobland.security.SecurityUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BackgroundService {
    private final BackgroundRepository backgroundRepository;
    private final SecurityUtils securityUtils;

    public Background createBackground(BackgroundDto.BackgroundCreate data) {
        try {
            Background back = new Background();

            back.setBackType(data.getBackType());
            back.setBackName(data.getBackName());
            back.setBackDesc(data.getBackDesc());
            back.setBackStart(data.getBackStart());
            back.setBackEnd(data.getBackEnd());
            back.setMemberId(securityUtils.getCurrentUser().getId());

            return backgroundRepository.save(back);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, AppErrors.CREATE_FAILED);
        }
    }
}
