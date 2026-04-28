package com.classifyName.nameClassifier;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class APIVersionConfig implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String version = request.getHeader("X-API-Version");

        if (version == null || version.isBlank()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");

            response.getWriter().write("""
                {
                  "status": "error",
                  "message": "API version header required"
                }
            """);

            return false; // 🔥 stop request here
        }

        return true;
    }
}
