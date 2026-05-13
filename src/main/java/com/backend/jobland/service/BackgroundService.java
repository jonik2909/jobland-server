package com.backend.jobland.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.backend.jobland.dto.BackgroundDto;
import com.backend.jobland.entity.Background;
import com.backend.jobland.lib.AppErrors;
import com.backend.jobland.lib.AppUtils;
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

    public Background updateBackground(String id, BackgroundDto.BackgroundUpdate data) {
        String memberId = securityUtils.getCurrentUser().getId();

        Background back = backgroundRepository.findByIdAndMemberId(id, memberId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, AppErrors.DATA_NOT_FOUND));

        AppUtils.copyNonNulls(data, back);

        return backgroundRepository.save(back);
    }

    public List<Background> getMyBackgrounds() {
        String memberId = securityUtils.getCurrentUser().getId();

        return backgroundRepository.findBackgroundsByMemberId(memberId);

    }

    public Map<String, Boolean> deleteBackground(String id) {
        String memberId = securityUtils.getCurrentUser().getId();

        Background back = backgroundRepository.findByIdAndMemberId(id, memberId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, AppErrors.DATA_NOT_FOUND));

        backgroundRepository.delete(back);

        return Map.of("success", true);
    }
}
