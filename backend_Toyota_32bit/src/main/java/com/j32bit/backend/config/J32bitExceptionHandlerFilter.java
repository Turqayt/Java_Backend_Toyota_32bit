package com.j32bit.backend.config;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j32bit.backend.annotation.NoLogging;
import com.j32bit.backend.shared.GenericResponse;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * This class is used to catch exceptions which are thrown before reaching the endpoint (on filter chain)
 * To catch exceptions from endpoint requests
 */

@Component
@NoLogging
@RequiredArgsConstructor
public class J32bitExceptionHandlerFilter extends OncePerRequestFilter {

    private final Environment env;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {

            filterChain.doFilter(request, response);

        } catch (ExpiredJwtException ex) {

            GenericResponse<Object> description = GenericResponse.createErrorResponse().code("EXPIRED").description("JWT token is expired");

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write(convertObjectToJson(description));

        } catch (Exception ex) {

            GenericResponse<Object> description = GenericResponse.createErrorResponse().code(ex.getMessage()).description(env.getProperty(ex.getMessage()));

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write(convertObjectToJson(description));
        }

    }

    public String convertObjectToJson(Object object) throws JsonProcessingException {

        if (object == null) {
            return null;
        }

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

}
