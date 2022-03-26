package com.kuropatin.zenbooking.controller;

import com.kuropatin.zenbooking.model.request.AmountRequest;
import com.kuropatin.zenbooking.model.request.UserEditRequest;
import com.kuropatin.zenbooking.model.response.ErrorResponse;
import com.kuropatin.zenbooking.model.response.UserResponse;
import com.kuropatin.zenbooking.security.util.AuthenticationUtils;
import com.kuropatin.zenbooking.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/api/v1/profile", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Profile Controller", description = "Actions for user profile")
public class ProfileController {

    private final UserService service;
    private final AuthenticationUtils authenticationUtils;

    @Operation(summary = "Get info of logged user", description = "Description")
    @ApiResponse(responseCode = "200", description = "OK", content = {
            @Content(schema = @Schema(implementation = UserResponse.class))
    })
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
            @Content(schema = @Schema(implementation = ErrorResponse.class))
    })
    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
            @Content(schema = @Schema(implementation = ErrorResponse.class))
    })
    @GetMapping
    public ResponseEntity<UserResponse> getUserInfo() {
        final long userId = authenticationUtils.getId();
        return new ResponseEntity<>(service.transformToNewUserResponse(service.getUserById(userId)), HttpStatus.OK);
    }

    @Operation(summary = "Update info of logged user", description = "Description")
    @ApiResponse(responseCode = "200", description = "User Info Updated Successfully", content = {
            @Content(schema = @Schema(implementation = UserResponse.class))
    })
    @ApiResponse(responseCode = "400", description = "Bad Request", content = {
            @Content(schema = @Schema(implementation = ErrorResponse.class))
    })
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
            @Content(schema = @Schema(implementation = ErrorResponse.class))
    })
    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
            @Content(schema = @Schema(implementation = ErrorResponse.class))
    })
    @PutMapping
    public ResponseEntity<UserResponse> updateUserInfo(@Valid @RequestBody final UserEditRequest userEditRequest) {
        final long userId = authenticationUtils.getId();
        return new ResponseEntity<>(service.transformToNewUserResponse(service.updateUser(userId, userEditRequest)), HttpStatus.OK);
    }

    @Operation(summary = "Deposit money to logged user's account", description = "Description")
    @ApiResponse(responseCode = "200", description = "Money Deposited Successfully", content = {
            @Content(schema = @Schema(implementation = UserResponse.class))
    })
    @ApiResponse(responseCode = "400", description = "Bad Request", content = {
            @Content(schema = @Schema(implementation = ErrorResponse.class))
    })
    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
            @Content(schema = @Schema(implementation = ErrorResponse.class))
    })
    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
            @Content(schema = @Schema(implementation = ErrorResponse.class))
    })
    @PutMapping("/deposit")
    public ResponseEntity<UserResponse> deposit(@Valid @RequestBody final AmountRequest amountRequest) {
        final long userId = authenticationUtils.getId();
        return new ResponseEntity<>(service.transformToNewUserResponse(service.deposit(userId, amountRequest)), HttpStatus.OK);
    }
}