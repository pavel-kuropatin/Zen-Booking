package com.kuropatin.bookingapp.controller;

import com.kuropatin.bookingapp.model.request.AmountRequest;
import com.kuropatin.bookingapp.model.request.UserEditRequest;
import com.kuropatin.bookingapp.model.response.UserResponse;
import com.kuropatin.bookingapp.security.util.AuthenticationUtils;
import com.kuropatin.bookingapp.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
@Api(value = "Profile Controller", tags = "User Profile - actions for user profile")
public class ProfileController {

    private final UserService service;
    private final AuthenticationUtils authenticationUtils;

    @ApiOperation(value = "Get info of logged user")
    @ApiImplicitParam(name = "X-Auth-Token", dataTypeClass = String.class, paramType = "header", value = "JWT Authentication Token", required = true)
    @GetMapping
    public ResponseEntity<UserResponse> getUserInfo() {
        long userId = authenticationUtils.getId();
        return new ResponseEntity<>(UserResponse.transformToNewUserResponse(service.getUserById(userId)), HttpStatus.OK);
    }

    @ApiOperation(value = "Update info of logged user")
    @ApiImplicitParam(name = "X-Auth-Token", dataTypeClass = String.class, paramType = "header", value = "JWT Authentication Token", required = true)
    @PutMapping
    public ResponseEntity<UserResponse> updateUserInfo(@Valid @RequestBody final UserEditRequest userEditRequest) {
        long userId = authenticationUtils.getId();
        return new ResponseEntity<>(UserResponse.transformToNewUserResponse(service.updateUser(userId, userEditRequest)), HttpStatus.OK);
    }

    @ApiOperation(value = "Deposit money to logged user's account")
    @ApiImplicitParam(name = "X-Auth-Token", dataTypeClass = String.class, paramType = "header", value = "JWT Authentication Token", required = true)
    @PutMapping("/deposit")
    public ResponseEntity<UserResponse> deposit(@Valid @RequestBody final AmountRequest amountRequest) {
        long userId = authenticationUtils.getId();
        return new ResponseEntity<>(UserResponse.transformToNewUserResponse(service.deposit(userId, amountRequest)), HttpStatus.CREATED);
    }
}