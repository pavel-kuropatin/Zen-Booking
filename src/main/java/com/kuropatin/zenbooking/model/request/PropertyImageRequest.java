package com.kuropatin.zenbooking.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PropertyImageRequest {

    @NotBlank(message = "Enter image URL")
    @Size(min = 1, max = 250, message = "Image URL should be 250 characters or less")
    private String imgUrl;
}