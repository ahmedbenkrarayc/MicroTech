package com.microtech.microtech.security.filter;

import com.microtech.microtech.security.service.SessionService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AuthFilter extends HttpFilter {
    private final SessionService sessionService;

    @Override
    protected void doFilter(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

        String path = request.getRequestURI();

        if(path.equals("/login") || path.startsWith("/public")) {
            chain.doFilter(request, response);
            return;
        }

        if (!sessionService.isLoggedIn()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"status\":401,\"message\":\"Unauthorized\",\"timestamp\":\"" + java.time.LocalDateTime.now() + "\"}");
            return;
        }

        chain.doFilter(request, response);
    }
}
