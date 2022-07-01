package com.j32bit.backend.service;

import com.j32bit.backend.dto.user.UserLoginRequestDTO;

public interface AuthenticationService {
    /**
     *
     * @param userLoginRequestDTO
     * @return generated token to access the other secured resources
     */
    String authenticate(UserLoginRequestDTO userLoginRequestDTO);
}
