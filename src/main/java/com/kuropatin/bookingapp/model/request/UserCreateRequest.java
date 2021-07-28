package com.kuropatin.bookingapp.model.request;

import com.kuropatin.bookingapp.model.Gender;
import com.kuropatin.bookingapp.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserCreateRequest {

    private String login;
    private String password;
    private String name;
    private String surname;
    private Gender gender;
    private LocalDate birthDate;
    private String email;
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