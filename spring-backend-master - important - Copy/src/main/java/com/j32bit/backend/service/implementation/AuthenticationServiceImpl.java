package com.j32bit.backend.service.implementation;

import com.j32bit.backend.config.J32bitUserDetailsServiceImpl;
import com.j32bit.backend.dto.user.UserLoginRequestDTO;
import com.j32bit.backend.service.AuthenticationService;
import com.j32bit.backend.utility.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional(readOnly = true)
public class AuthenticationServiceImpl implements AuthenticationService {
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    private final J32bitUserDetailsServiceImpl j32bitUserDetailsService;
    @Override
    public String authenticate(UserLoginRequestDTO userLoginRequestDTO) {
        Authentication authentication = authenticateToken(userLoginRequestDTO);

        final User user = (User) authentication.getPrincipal();


        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenUtil.generateToken(user.getUsername());
    }

    private Authentication authenticateToken(UserLoginRequestDTO userLoginRequestDTO) {

        log.info("Authenticating for user: {}", userLoginRequestDTO.getUsername());

        UserDetails storedUserDetails = j32bitUserDetailsService.loadUserByUsername(userLoginRequestDTO.getUsername());

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                storedUserDetails, userLoginRequestDTO.getPassword(), storedUserDetails.getAuthorities());

        return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }
}
