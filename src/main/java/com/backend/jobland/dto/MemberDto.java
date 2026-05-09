package com.backend.jobland.dto;

import com.backend.jobland.lib.AppErrors;
import com.backend.jobland.lib.enums.CategoryType;
import com.backend.jobland.lib.enums.Country;
import com.backend.jobland.lib.enums.MemberFeatured;
import com.backend.jobland.lib.enums.MemberSort;
import com.backend.jobland.lib.enums.MemberType;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

public class MemberDto {

    @Data
    public static class Signup {
        @NotBlank()
        @Size(min = 2, max = 15)
        private String memberNick;

        @NotBlank()
        @Size(min = 2, max = 15)
        private String memberPassword;

        @NotBlank()
        @Size(min = 9, max = 15)
        @Pattern(regexp = "^[+]?[0-9]+$", message = AppErrors.INVALID_PHONE_NUMBER)
        private String memberPhone;

        @NotNull()
        private MemberType memberType;

    }

    @Data
    public static class Login {
        @NotBlank()
        @Size(min = 2, max = 15)
        private String memberNick;

        @NotBlank()
        @Size(min = 2, max = 15)
        private String memberPassword;
    }

    @Data
    public static class UpdateMember {
        @Size(min = 2, max = 15)
        private String memberNick;

        @Size(min = 9, max = 15)
        @Pattern(regexp = "^[+]?[0-9]+$", message = AppErrors.INVALID_PHONE_NUMBER)
        private String memberPhone;

        private Integer memberAge;

        @Email()
        private String memberEmail;

        private String memberImage;

        private String memberWebsite;

        private String memberTeamSize;

        private Country memberCountry;

        private String memberCity;

        private String memberDesc;

        private String memberSalary;

        private String memberExperience;

        private String memberLanguage;

        private Double memberHourRate;

        private CategoryType memberCategory;
    }

    @Data
    public static class MembersInquiry {
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

        private String search;
    }
}
