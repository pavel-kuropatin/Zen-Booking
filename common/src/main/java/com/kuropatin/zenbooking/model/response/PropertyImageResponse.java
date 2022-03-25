package com.kuropatin.zenbooking.model.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PropertyImageResponse {

    private long id;
    private String imgUrl;

    @SneakyThrows
    @Override
    public String toString() {
        return new ObjectMapper().writeValueAsString(this);
    }
}