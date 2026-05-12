package com.backend.jobland.dto;

import com.backend.jobland.lib.enums.CategoryType;
import com.backend.jobland.lib.enums.Country;
import com.backend.jobland.lib.enums.JobLevel;
import com.backend.jobland.lib.enums.JobSort;
import com.backend.jobland.lib.enums.JobStatus;
import com.backend.jobland.lib.enums.JobType;

import jakarta.validation.constraints.Min;
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

    @Data
    public static class JobUpdate {
        private String jobTitle;
        private String jobDesc;
        private JobType jobType;
        private String jobRequirement;
        private String jobExpertise;
        private Country jobCountry;
        private String jobCity;
        private String jobAddress;
        private String jobSalary;
        private JobLevel jobLevel;
        private String jobExperience;
        private Double jobHourRate;
        private String jobDeadline;
        private CategoryType jobCategory;
        private JobStatus jobStatus;
    }

    @Data
    public static class CompanyJobsInquiry {

        @NotNull()
        @Min(1)
        private Integer page;

        @NotNull()
        @Min(1)
        private Integer limit;

        private JobSort sort;

        private JobType jobType;

        private JobLevel jobLevel;

        private Country jobCountry;

        private CategoryType jobCategory;

        private JobStatus jobStatus;

        private String search;

    }

}
