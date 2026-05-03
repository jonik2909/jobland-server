package com.backend.jobland.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

public class MemberDto {
    @Data
    public static class Login {
        @NotBlank()
        @Size(min = 2, max = 15)
        private String memberNick;

        @NotBlank()
        private String memberPassword;
    }
}
