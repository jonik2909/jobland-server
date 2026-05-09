package com.backend.jobland.dto;

import com.backend.jobland.lib.enums.CategoryType;
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

}
