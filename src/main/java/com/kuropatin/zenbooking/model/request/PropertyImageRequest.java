package com.kuropatin.zenbooking.model.request;

import com.kuropatin.zenbooking.util.ToStringUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyImageRequest implements Request {

    @NotBlank(message = "Enter image URL")
    @Size(min = 1, max = 250, message = "Image URL should be 250 characters or less")
    private String imgUrl;

    @Override
    public String toString() {
        return ToStringUtils.toJsonString(this);
    }
}