package com.kuropatin.bookingapp.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PropertyImageRequest {

    @NotEmpty(message = "Enter image URL")
    @NotBlank(message = "Enter image URL")
    @Size(min = 1, max = 100, message = "Image URL should be 250 characters or less")
    private String imgUrl;
}