package com.backend.jobland.security;

import com.backend.jobland.lib.enums.MemberType;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JoblandPrincipal {
    private String id;
    private String memberNick;
    private MemberType memberType;
}
