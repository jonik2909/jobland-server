package com.backend.jobland.dto;

import com.backend.jobland.lib.enums.CategoryType;
import com.backend.jobland.lib.enums.Country;
import com.backend.jobland.lib.enums.JobLevel;
import com.backend.jobland.lib.enums.JobType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

public class CompanyDto {

    @Data
    public static class JobCreate {
        @NotBlank()
        private String jobTitle;

        @NotBlank()
        private String jobDesc;

        @NotNull()
        private JobType jobType;

        @NotBlank()
        private String jobRequirement;

        @NotBlank()
        private String jobExpertise;

        @NotNull()
        private Country jobCountry;

        @NotBlank()
        private String jobCity;

        @NotBlank()
        private String jobAddress;

        @NotBlank()
        private String jobSalary;

        @NotNull()
        private JobLevel jobLevel;

        @NotBlank()
        private String jobExperience;

        @NotNull()
        private Double jobHourRate;

        @NotBlank()
        private String jobDeadline;

        @NotNull()
        private CategoryType jobCategory;
    }

}
