package com.kuropatin.zenbooking.controller;

import com.kuropatin.zenbooking.security.request.LoginRequest;
import com.kuropatin.zenbooking.security.response.LoginResponse;
import com.kuropatin.zenbooking.security.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private final LoginService loginService;

    @ApiOperation(value = "Login page")
    @PostMapping
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody final LoginRequest loginRequest) {
        return new ResponseEntity<>(loginService.login(loginRequest), HttpStatus.OK);
    }
}