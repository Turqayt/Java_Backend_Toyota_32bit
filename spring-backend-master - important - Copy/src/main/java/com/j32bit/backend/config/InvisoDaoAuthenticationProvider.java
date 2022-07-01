package com.j32bit.backend.config;

import com.j32bit.backend.utility.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

@RequiredArgsConstructor
@Log4j2
public class InvisoDaoAuthenticationProvider extends DaoAuthenticationProvider {



    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

        try {
            super.additionalAuthenticationChecks(userDetails, authentication);
        } catch (BadCredentialsException e) {
            log.info("Login attempt on username: {} from Client IP: {} Server IP: {}", userDetails.getUsername(), SessionManager.remoteAddress(), SessionManager.localAddress());
            throw new BadCredentialsException("action.Login.js.msg.wrongPassword");
        }
    }








}
