package com.kuropatin.zenbooking.security.service;

import com.kuropatin.zenbooking.security.request.LoginRequest;
import com.kuropatin.zenbooking.security.response.LoginResponse;
import com.kuropatin.zenbooking.security.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    public LoginResponse login(final LoginRequest loginRequest) {
        authenticate(loginRequest);
        return buildLoginResponse(loginRequest);
    }

    private void authenticate(final LoginRequest loginRequest) {
        final Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authenticate);
    }

    private LoginResponse buildLoginResponse(final LoginRequest loginRequest) {
        return new LoginResponse(
                jwtUtils.generateToken(userDetailsService.loadUserByUsername(loginRequest.getLogin())),
                loginRequest.getLogin()
        );
    }
}