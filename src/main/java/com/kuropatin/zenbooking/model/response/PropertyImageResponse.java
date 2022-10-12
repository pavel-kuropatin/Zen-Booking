package com.kuropatin.zenbooking.model.response;

import com.kuropatin.zenbooking.util.ToStringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PropertyImageResponse {

    private Long id;
    private String imgUrl;

    @Override
    public String toString() {
        return ToStringUtils.toJsonString(this);
    }
}