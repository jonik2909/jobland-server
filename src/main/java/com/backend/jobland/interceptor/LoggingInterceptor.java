package com.backend.jobland.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class LoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);

        String method = request.getMethod();
        String uri = request.getRequestURI();

        log.info("[REQUEST]  {} {}", method, uri);

        if (handler instanceof HandlerMethod handlerMethod) {
            String controllerName = handlerMethod.getBeanType().getSimpleName();
            String methodName = handlerMethod.getMethod().getName();
            log.info("[API]      {}.{}", controllerName, methodName);
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) {
        Long startTime = (Long) request.getAttribute("startTime");
        long duration = (startTime != null) ? System.currentTimeMillis() - startTime : 0;
        int status = response.getStatus();

        // Optional: Log error if exception occurred
        if (ex != null) {
            log.error("[ERROR]    {}", ex.getMessage());
        }

        log.info("[RESPONSE] {} ({}ms)", status, duration);
    }
}
