package com.kuropatin.bookingapp.security.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginRequest {

    @NotBlank(message = "Enter login")
    @Size(min = 2, max = 20, message = "Login should be between 2 and 20 characters")
    private String login;

    @NotBlank(message = "Enter password")
    @Size(min = 8, max = 20, message = "Password should be between 8 and 20 characters")
    private String password;
}