package com.kuropatin.bookingapp.model.response;

import com.kuropatin.bookingapp.model.Gender;
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
}