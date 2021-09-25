package com.kuropatin.zenbooking.controller;

import com.kuropatin.zenbooking.model.request.UserCreateRequest;
import com.kuropatin.zenbooking.model.response.UserResponse;
import com.kuropatin.zenbooking.service.UserService;
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
@RequestMapping("/register")
@RequiredArgsConstructor
@Api(value = "Registration Controller", tags = "Registration and Login", description = "New user registration")
public class RegistrationController {

    private final UserService userService;

    @ApiOperation(value = "User registration page")
    @PostMapping
    public ResponseEntity<UserResponse> create(@Valid @RequestBody final UserCreateRequest userCreateRequest) {
        return new ResponseEntity<>(userService.transformToNewUserResponse(userService.createUser(userCreateRequest)), HttpStatus.CREATED);
    }
}