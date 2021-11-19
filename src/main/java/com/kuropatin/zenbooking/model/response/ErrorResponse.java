package com.kuropatin.zenbooking.model.response;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ErrorResponse extends AbstractErrorResponse {

    private String message;

    public ErrorResponse(final Exception e, final HttpStatus status) {
        super(e, status);
        this.message = e.getMessage();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}