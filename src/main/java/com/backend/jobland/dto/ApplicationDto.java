package com.backend.jobland.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

public class ApplicationDto {

    @Data
    public static class ApplicationSubmit {
        @NotBlank()
        private String jobId;
    }
}
