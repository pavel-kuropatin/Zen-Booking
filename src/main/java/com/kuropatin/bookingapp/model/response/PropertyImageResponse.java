package com.kuropatin.bookingapp.model.response;

import com.kuropatin.bookingapp.model.PropertyImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PropertyImageResponse {

    private long id;
    private String imgUrl;

    public static PropertyImageResponse transformToNewPropertyImageResponse(PropertyImage propertyImage) {
        PropertyImageResponse propertyImageResponse = new PropertyImageResponse();
        transformToPropertyImageResponse(propertyImage, propertyImageResponse);
        return propertyImageResponse;
    }

    public static List<PropertyImageResponse> transformToListPropertyImageResponse(List<PropertyImage> propertyImages) {
        List<PropertyImageResponse> propertyImageResponseList = new ArrayList<>();
        for(PropertyImage propertyImage : propertyImages) {
            propertyImageResponseList.add(transformToNewPropertyImageResponse(propertyImage));
        }
        return propertyImageResponseList;
    }

    private static PropertyImageResponse transformToPropertyImageResponse(PropertyImage propertyImage, PropertyImageResponse propertyImageResponse) {
        propertyImageResponse.setId(propertyImage.getId());
        propertyImageResponse.setImgUrl(propertyImage.getImgUrl());
        return propertyImageResponse;
    }
}