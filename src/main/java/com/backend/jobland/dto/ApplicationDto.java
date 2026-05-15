package com.backend.jobland.dto;

import com.backend.jobland.lib.enums.ApplicationStatus;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

public class ApplicationDto {

    @Data
    public static class ApplicationSubmit {
        @NotBlank()
        private String jobId;
    }

    @Data
    public static class ApplicationsInquiry {
        @NotNull()
        @Min(1)
        private Integer page;

        @NotNull()
        @Min(1)
        private Integer limit;

        private ApplicationStatus applicationStatus;

    }
}
