package com.kuropatin.bookingapp.model.dto;

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
public class UserDto {

    private String login;
    private String password;
    private String name;
    private String surname;
    private Gender gender;
    private LocalDate birthDate;
    private String email;
    private String phone;

    public static User transformToNewUser(UserDto userDto) {
        User user = new User();
        transformToUser(userDto, user);
        return user;
    }

    public static User transformToUser(UserDto userDto, User user) {
        user.setLogin(userDto.getLogin());
        user.setPassword(userDto.getPassword());
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setGender(userDto.getGender());
        user.setBirthDate(userDto.getBirthDate());
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhone());
        return user;
    }
}