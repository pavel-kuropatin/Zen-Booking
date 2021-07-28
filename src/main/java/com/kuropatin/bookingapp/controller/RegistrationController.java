package com.kuropatin.bookingapp.controller;

import com.kuropatin.bookingapp.model.request.UserCreateRequest;
import com.kuropatin.bookingapp.model.response.UserResponse;
import com.kuropatin.bookingapp.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    @ApiOperation(value = "User registration")
    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody final UserCreateRequest userCreateRequest) {
        return new ResponseEntity<>(UserResponse.transformToNewUserResponse(userService.createUser(userCreateRequest)), HttpStatus.CREATED);
    }
}