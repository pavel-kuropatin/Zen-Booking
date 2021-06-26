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
public class UserRequest {

    private String login;
    private String password;
    private String name;
    private String surname;
    private Gender gender;
    private LocalDate birthDate;
    private String email;
    private String phone;

    public static User transformToNewUser(UserRequest userRequest) {
        User user = new User();
        transformToUser(userRequest, user);
        return user;
    }

    public static User transformToUser(UserRequest userRequest, User user) {
        user.setLogin(userRequest.getLogin());
        user.setPassword(userRequest.getPassword());
        user.setName(userRequest.getName());
        user.setSurname(userRequest.getSurname());
        user.setGender(userRequest.getGender());
        user.setBirthDate(userRequest.getBirthDate());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());
        return user;
    }
}