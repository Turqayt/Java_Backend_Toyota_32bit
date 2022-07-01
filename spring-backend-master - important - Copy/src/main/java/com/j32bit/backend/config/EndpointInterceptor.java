package com.j32bit.backend.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * Intercepts registered endpoints for logging purposes
 */
@Log4j2
@Component
@Scope(value = "SCOPE_PROTOTYPE")
public class EndpointInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String name = SecurityContextHolder.getContext().getAuthentication().getName();


        if(!name.isEmpty()) {
            log.info("{} is making request to {}. IP: {}", name, request.getRequestURI(), request.getRemoteHost());
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);

        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        if(!name.isEmpty()) {
            log.info("{} made request to {}. IP: {}", name, request.getRequestURI(), request.getRemoteHost());
        }
    }

}
