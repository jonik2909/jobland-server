package com.backend.jobland.dto;

import java.time.LocalDate;

import com.backend.jobland.lib.enums.BackgroundType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

public class BackgroundDto {
    @Data
    public static class BackgroundCreate {
        @NotNull()
        private BackgroundType backType;

        @NotBlank()
        private String backName;

        @NotBlank()
        private String backDesc;

        @NotNull()
        private LocalDate backStart;

        @NotNull()
        private LocalDate backEnd;
    }

    @Data
    public static class BackgroundUpdate {
        private BackgroundType backType;

        private String backName;

        private String backDesc;

        private LocalDate backStart;

        private LocalDate backEnd;
    }
}
