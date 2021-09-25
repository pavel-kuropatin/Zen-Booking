package com.kuropatin.zenbooking.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
public class UserCreateRequest extends UserEditRequest {

    @NotBlank(message = "Enter login")
    @Size(min = 2, max = 20, message = "Login should be between 2 and 20 characters")
    private String login;

    @NotBlank(message = "Enter password")
    @Size(min = 8, max = 20, message = "Password should be between 8 and 20 characters")
    private String password;

    public UserCreateRequest(
            String login,
            String password,
            String name,
            String surname,
            String gender,
            String birthDate,
            String email,
            String phone
    ) {
        super(name, surname, gender, birthDate, email, phone);
        this.login = login;
        this.password = password;
    }
}