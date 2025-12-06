package com.microtech.microtech.security.interceptor;

import com.microtech.microtech.security.annotation.RolesAllowed;
import com.microtech.microtech.security.exception.ForbiddenException;
import com.microtech.microtech.security.exception.UnauthorizedException;
import com.microtech.microtech.security.service.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class RoleInterceptor implements HandlerInterceptor {
    private final SessionService sessionService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler
    ) {
        if(!(handler instanceof HandlerMethod handlerMethod)){
            return true;
        }

        RolesAllowed annotation = handlerMethod.getMethodAnnotation(RolesAllowed.class);
        if(annotation == null){
            return true;
        }

        String currentUserRole = sessionService.getUserRole();
        if(currentUserRole == null){
            throw new UnauthorizedException("You must be logged in.");
        }

        boolean allowed = Arrays.stream(annotation.roles())
                .anyMatch(role -> role.equalsIgnoreCase(currentUserRole));

        if(!allowed){
            throw new ForbiddenException();
        }

        return true;
    }
}
