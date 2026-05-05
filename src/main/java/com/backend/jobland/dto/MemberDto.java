package com.backend.jobland.dto;

import com.backend.jobland.lib.AppErrors;
import com.backend.jobland.lib.enums.MemberType;

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
}
