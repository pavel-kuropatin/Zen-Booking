package com.kuropatin.bookingapp.model.response;

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
public class UserResponse {

    private long id;
    private String name;
    private String surname;
    private Gender gender;
    private LocalDate birthDate;
    private String email;
    private String phone;
    private int balance;

    public static UserResponse transformToNewUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        transformToUserResponse(user, userResponse);
        return userResponse;
    }

    public static UserResponse transformToUserResponse(User user, UserResponse userResponse) {
        userResponse.setId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setSurname(user.getSurname());
        userResponse.setGender(user.getGender());
        userResponse.setBirthDate(user.getBirthDate());
        userResponse.setEmail(user.getEmail());
        userResponse.setPhone(user.getPhone());
        userResponse.setBalance(user.getBalance());
        return userResponse;
    }
}