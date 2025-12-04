package com.microtech.microtech.security.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class SessionService {
    private final String USER_ID_KEY = "USER_ID";
    private final String USER_ROLE_KEY = "ROLE_ID";

    private HttpSession getSession() {
        ServletRequestAttributes attr =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attr != null ? attr.getRequest().getSession(false) : null;
    }

    public void setUser(Long userId, String role) {
        HttpSession session = getSession();
        if (session != null) {
            session.setAttribute(USER_ID_KEY, userId);
            session.setAttribute(USER_ROLE_KEY, role);
        }
    }

    public Long getUserId() {
        HttpSession session = getSession();
        if (session == null) return null;

        Object value = session.getAttribute(USER_ID_KEY);
        return value == null ? null : (Long) value;
    }

    public String getUserRole() {
        HttpSession session = getSession();
        if (session == null) return null;

        Object value = session.getAttribute(USER_ROLE_KEY);
        return value == null ? null : value.toString();
    }

    public boolean isLoggedIn() {
        HttpSession session = getSession();
        return session != null && session.getAttribute(USER_ID_KEY) != null;
    }

    public void clear() {
        HttpSession session = getSession();
        if (session != null) {
            session.removeAttribute(USER_ID_KEY);
            session.removeAttribute(USER_ROLE_KEY);
        }
    }
}
