package com.kuropatin.zenbooking.model.response;

import com.kuropatin.zenbooking.model.PropertyType;
import com.kuropatin.zenbooking.util.ToStringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PropertyResponse {

    private Long id;
    private PropertyType type;
    private String name;
    private String description;
    private String address;
    private Integer price;
    private Short guests;
    private Short rooms;
    private Short beds;
    private Boolean hasKitchen;
    private Boolean hasWasher;
    private Boolean hasTv;
    private Boolean hasInternet;
    private Boolean isPetsAllowed;
    private Boolean isAvailable;
    private String rating;

    @Override
    public String toString() {
        return ToStringUtils.toJsonString(this);
    }
}