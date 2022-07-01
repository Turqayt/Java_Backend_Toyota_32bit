package com.j32bit.backend.utility;

import com.j32bit.backend.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

/**
 * Eases to acquire information from session and security context
 */
public class SessionManager {

    private SessionManager() {}

    public static Object getAttribute(String name) {

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

        // if session is expired, return message before redirecting to login screen
        HttpSession session = attr.getRequest().getSession(false);
        if (session == null) {
            throw new InvisoException("session.expired");
        }

        return session.getAttribute(name);
    }


    /**
     * Refreshes the session by calling it. Calling it updates the last accessed time.
     */
    public static void refresh() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        attr.getRequest().getSession(false);
    }


    public static void setAttribute(String name, Object value) {

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(true);

        session.setAttribute(name, value);
    }


    public static void removeAttribute(String name) {

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(true);

        session.removeAttribute(name);
    }


    public static String remoteAddress() {

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getRemoteAddr();
    }


    public static String localAddress() {

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getLocalAddr();
    }


    public static User cvqsUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
