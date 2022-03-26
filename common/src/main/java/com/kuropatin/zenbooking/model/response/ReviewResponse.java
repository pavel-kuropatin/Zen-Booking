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
public class ReviewResponse {

    private long id;
    private long propertyId;
    private String summary;
    private String description;
    private byte rating;

    @Override
    public String toString() {
        return ToStringUtils.toJsonString(this);
    }
}