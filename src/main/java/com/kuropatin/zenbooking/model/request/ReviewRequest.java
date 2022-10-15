package com.kuropatin.zenbooking.model.request;

import com.kuropatin.zenbooking.util.ToStringUtils;
import com.kuropatin.zenbooking.validation.ShortInRange;
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
public class ReviewRequest implements Request {

    @NotBlank(message = "Enter summary")
    @Size(max = 100, message = "Summary should be between 500 characters or lower")
    private String summary;

    @NotBlank(message = "Enter description")
    @Size(max = 500, message = "Description should be between 500 characters or lower")
    private String description;

    @ShortInRange(min = 1, max = 5, message = "Rating should be between 1 and 10")
    private Byte rating;

    @Override
    public String toString() {
        return ToStringUtils.toJsonString(this);
    }
}