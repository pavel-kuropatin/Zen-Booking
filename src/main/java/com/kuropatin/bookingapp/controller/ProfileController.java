package com.kuropatin.bookingapp.controller;

import com.kuropatin.bookingapp.model.request.AmountRequest;
import com.kuropatin.bookingapp.model.request.UserRequest;
import com.kuropatin.bookingapp.model.response.UserResponse;
import com.kuropatin.bookingapp.security.util.AuthenticationUtils;
import com.kuropatin.bookingapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final UserService service;
    private final AuthenticationUtils authenticationUtils;

    @GetMapping
    public ResponseEntity<UserResponse> getUserInfo() {
        long userId = authenticationUtils.getId();
        return new ResponseEntity<>(UserResponse.transformToNewUserResponse(service.getUserById(userId)), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UserResponse> updateUserInfo(@RequestBody final UserRequest userRequest) {
        long userId = authenticationUtils.getId();
        return new ResponseEntity<>(UserResponse.transformToNewUserResponse(service.updateUser(userId, userRequest)), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> delete() {
        long userId = authenticationUtils.getId();
        return new ResponseEntity<>(service.softDeleteUser(userId), HttpStatus.OK);
    }

    @PutMapping("/deposit")
    public ResponseEntity<UserResponse> deposit(@RequestBody final AmountRequest amountRequest) {
        long userId = authenticationUtils.getId();
        return new ResponseEntity<>(UserResponse.transformToNewUserResponse(service.deposit(userId, amountRequest)), HttpStatus.CREATED);
    }
}