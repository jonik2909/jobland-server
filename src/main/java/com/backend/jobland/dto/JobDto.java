package com.backend.jobland.dto;

import com.backend.jobland.lib.enums.CategoryType;
import com.backend.jobland.lib.enums.Country;
import com.backend.jobland.lib.enums.JobLevel;
import com.backend.jobland.lib.enums.JobSort;
import com.backend.jobland.lib.enums.JobType;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

public class JobDto {

    @Data
    public static class JobsInquiry {

        @NotNull()
        @Min(1)
        private Integer page;

        @NotNull()
        @Min(1)
        private Integer limit;

        private JobSort sort;

        private String companyId;

        private JobType jobType;

        private JobLevel jobLevel;

        private Country jobCountry;

        private CategoryType jobCategory;

        private String search;

    }
}
