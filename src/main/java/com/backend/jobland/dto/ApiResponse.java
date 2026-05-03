package com.backend.jobland.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
    private int code;
    private String message;

    public static ApiResponse error(int code, String message) {
        return ApiResponse.builder().code(code).message(message).build();
    }

}
