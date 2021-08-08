package com.kuropatin.bookingapp.security.controller;

import com.kuropatin.bookingapp.security.request.LoginRequest;
import com.kuropatin.bookingapp.security.response.LoginResponse;
import com.kuropatin.bookingapp.security.util.TokenUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
@Api(value = "Login Controller - login page", tags = "Registration and Login")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final TokenUtils tokenUtils;
    private final UserDetailsService userDetailsService;

    @ApiOperation(value = "Login page")
    @PostMapping
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        return ResponseEntity.ok(
                LoginResponse
                        .builder()
                        .token(tokenUtils.generateToken(userDetailsService.loadUserByUsername(loginRequest.getLogin())))
                        .login(loginRequest.getLogin())
                        .build()
        );
    }
}