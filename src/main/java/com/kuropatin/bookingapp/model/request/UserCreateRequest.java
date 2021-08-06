package com.kuropatin.bookingapp.model.request;

import com.kuropatin.bookingapp.model.Gender;
import com.kuropatin.bookingapp.model.User;
import com.kuropatin.bookingapp.validation.ValueOfEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserCreateRequest {

    @NotEmpty(message = "Enter login")
    @NotBlank(message = "Enter login")
    @Size(min = 2, max = 20, message = "Login should be between 2 and 20 characters")
    private String login;

    @NotEmpty(message = "Enter password")
    @NotBlank(message = "Enter password")
    @Size(min = 8, max = 20, message = "Password should be between 8 and 20 characters")
    private String password;

    @NotEmpty(message = "Enter name")
    @NotBlank(message = "Enter name")
    @Size(min = 2, max = 20, message = "Name should be between 2 and 20 characters")
    private String name;

    @NotEmpty(message = "Enter surname")
    @NotBlank(message = "Enter surname")
    @Size(min = 2, max = 20, message = "Surname should be between 2 and 20 characters")
    private String surname;

    @ValueOfEnum(enumClass = Gender.class, message = "Specify gender")
    private Gender gender;

    @Past(message = "Birth date should be valid")
    private LocalDate birthDate;

    @Email(message = "Email should be valid")
    @Size(max = 50, message = "Email should be 50 characters or less")
    private String email;

    @NotEmpty(message = "Enter phone number")
    @NotBlank(message = "Enter phone number")
    @Size(max = 20, message = "Phone number should be less than 20 characters")
    @Pattern(regexp = "^[+]\\d+$", message = "Phone number should start with + and contain only numbers")
    private String phone;

    public static User transformToNewUser(UserCreateRequest userCreateRequest) {
        User user = new User();
        transformToUser(userCreateRequest, user);
        return user;
    }

    public static User transformToUser(UserCreateRequest userCreateRequest, User user) {
        user.setLogin(userCreateRequest.getLogin());
        user.setPassword(userCreateRequest.getPassword());
        user.setName(userCreateRequest.getName());
        user.setSurname(userCreateRequest.getSurname());
        user.setGender(userCreateRequest.getGender());
        user.setBirthDate(userCreateRequest.getBirthDate());
        user.setEmail(userCreateRequest.getEmail());
        user.setPhone(userCreateRequest.getPhone());
        return user;
    }
}