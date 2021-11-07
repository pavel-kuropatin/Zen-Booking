package com.kuropatin.zenbooking.controller;

import com.kuropatin.zenbooking.security.request.LoginRequest;
import com.kuropatin.zenbooking.security.response.LoginResponse;
import com.kuropatin.zenbooking.security.util.JwtUtils;
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
@Api(value = "Login Controller", tags = "Registration and Login", description = "Login for registered user")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    @ApiOperation(value = "Login page")
    @PostMapping
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody final LoginRequest loginRequest) {
        final Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        return ResponseEntity.ok(
                LoginResponse
                        .builder()
                        .token(jwtUtils.generateToken(userDetailsService.loadUserByUsername(loginRequest.getLogin())))
                        .login(loginRequest.getLogin())
                        .build()
        );
    }
}