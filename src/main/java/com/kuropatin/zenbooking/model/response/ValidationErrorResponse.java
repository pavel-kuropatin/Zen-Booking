package com.kuropatin.zenbooking.model.response;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
@Setter
public class ValidationErrorResponse extends AbstractErrorResponse {

    private Map<String, String> errors;

    public ValidationErrorResponse(final Exception e, final HttpStatus status, final Map<String, String> errors) {
        super(e, status);
        this.errors = errors;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}