package com.kuropatin.zenbooking.controller;

import com.kuropatin.zenbooking.model.request.UserCreateRequest;
import com.kuropatin.zenbooking.model.response.ErrorResponse;
import com.kuropatin.zenbooking.model.response.UserResponse;
import com.kuropatin.zenbooking.security.request.LoginRequest;
import com.kuropatin.zenbooking.security.response.LoginResponse;
import com.kuropatin.zenbooking.security.service.LoginService;
import com.kuropatin.zenbooking.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Login And Registration Controller", description = "Login and registration of users")
public class LoginAndRegistrationController {

    private final LoginService loginService;
    private final UserService userService;

    @Operation(summary = "Login for registered user", description = "Description")
    @ApiResponse(responseCode = "200", description = "User Logged In", content = {
            @Content(schema = @Schema(implementation = LoginResponse.class))
    })
    @ApiResponse(responseCode = "400", description = "Bad Credentials", content = {
            @Content(schema = @Schema(implementation = ErrorResponse.class))
    })
    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
            @Content(schema = @Schema(implementation = ErrorResponse.class))
    })
    @PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody final LoginRequest loginRequest) {
        return new ResponseEntity<>(loginService.login(loginRequest), HttpStatus.OK);
    }

    @ApiResponse(responseCode = "201", description = "User Registered", content = {
            @Content(schema = @Schema(implementation = UserResponse.class))
    })
    @ApiResponse(responseCode = "400", description = "Bad User Input", content = {
            @Content(schema = @Schema(implementation = ErrorResponse.class))
    })
    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
            @Content(schema = @Schema(implementation = ErrorResponse.class))
    })
    @Operation(summary = "Registration of new user", description = "Description")
    @PostMapping(path = "/register", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> create(@Valid @RequestBody final UserCreateRequest userCreateRequest) {
        return new ResponseEntity<>(userService.transformToNewUserResponse(userService.createUser(userCreateRequest)), HttpStatus.CREATED);
    }
}