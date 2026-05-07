package com.backend.jobland.security;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.backend.jobland.lib.enums.MemberType;
import com.backend.jobland.service.AuthService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    private final AuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        String token = extractToken(request);

        if (StringUtils.hasText(token)) {
            try {
                Map<String, Object> claims = authService.parseToken(token);
                String memberId = (String) claims.get("id");
                String memberNick = (String) claims.get("memberNick");
                MemberType memberType = MemberType.valueOf((String) claims.get("memberType"));

                if (SecurityContextHolder.getContext().getAuthentication() == null && memberNick != null) {
                    JoblandPrincipal principal = new JoblandPrincipal(memberId, memberNick, memberType);

                    List<SimpleGrantedAuthority> authorities = Collections
                            .emptyList();
                    if (memberType != null) {
                        authorities = Collections.singletonList(
                                new SimpleGrantedAuthority(
                                        "ROLE_" + memberType.name())); // ROLE_ADMIN ROLE_CANDIDATE ROLE_COMPANY
                    }

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            principal, null, authorities);
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }

            } catch (Exception e) {
                log.error("JWT, error: {}", e.getMessage());
            }
        }

        filterChain.doFilter(request, response);

    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
