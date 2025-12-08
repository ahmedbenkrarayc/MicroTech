package com.microtech.microtech.security.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
@Order(1)
public class GlobalSecurityExceptionHandler {

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Map<String, Object>> handleUnauthorized(
            UnauthorizedException ex,
            HttpServletRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(Map.of(
                        "timestamp", LocalDateTime.now().toString(),
                        "status", 401,
                        "error", "Unauthorized",
                        "message", ex.getMessage(),
                        "path", request.getRequestURI()
                ));
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Map<String, Object>> handleForbidden(
            ForbiddenException ex,
            HttpServletRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(Map.of(
                        "timestamp", LocalDateTime.now().toString(),
                        "status", 403,
                        "error", "Forbidden",
                        "message", ex.getMessage(),
                        "path", request.getRequestURI()
                ));
    }
}
