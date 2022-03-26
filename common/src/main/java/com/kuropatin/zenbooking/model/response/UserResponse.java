package com.kuropatin.zenbooking.model.response;

import com.kuropatin.zenbooking.model.Gender;
import com.kuropatin.zenbooking.util.ToStringUtils;
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

    private Long id;
    private String name;
    private String surname;
    private Gender gender;
    private LocalDate birthDate;
    private String email;
    private String phone;
    private Integer balance;

    @Override
    public String toString() {
        return ToStringUtils.toJsonString(this);
    }
}