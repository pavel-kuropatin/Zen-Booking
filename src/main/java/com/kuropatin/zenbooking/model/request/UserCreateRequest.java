package com.kuropatin.zenbooking.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class UserCreateRequest extends UserEditRequest {

    @NotBlank(message = "Enter login")
    @Size(min = 2, max = 20, message = "Login should be between 2 and 20 characters")
    private String login;

    @NotBlank(message = "Enter password")
    @Size(min = 8, max = 20, message = "Password should be between 8 and 20 characters")
    private String password;
}