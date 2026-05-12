package com.backend.jobland.dto;

import com.backend.jobland.lib.enums.CategoryType;
import com.backend.jobland.lib.enums.Country;
import com.backend.jobland.lib.enums.JobLevel;
import com.backend.jobland.lib.enums.JobSort;
import com.backend.jobland.lib.enums.JobStatus;
import com.backend.jobland.lib.enums.JobType;
import com.backend.jobland.lib.enums.MemberFeatured;
import com.backend.jobland.lib.enums.MemberSort;
import com.backend.jobland.lib.enums.MemberStatus;
import com.backend.jobland.lib.enums.MemberType;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

public class AdminDto {
    @Data
    public static class AdminMembersInquiry {
        @NotNull()
        @Min(1)
        private Integer page;

        @NotNull()
        @Min(1)
        private Integer limit;

        private MemberSort sort;

        private MemberType memberType;

        private CategoryType memberCategory;

        private MemberFeatured memberFeatured;

        private MemberStatus memberStatus;

        private String search;
    }

    @Data
    public static class AdminMemberUpdate {
        private MemberStatus memberStatus;
        private MemberType memberType;
        private MemberFeatured memberFeatured;
    }

    @Data
    public static class AdminJobsInquiry {

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

        private JobStatus jobStatus;

        private String search;

    }

}
