package com.kuropatin.zenbooking.model.response;

import com.kuropatin.zenbooking.util.ApplicationTimestamp;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;
import java.util.Map;

@Getter
@Setter
public class ValidationErrorResponse {

    private Timestamp timestamp;
    private String status;
    private String exception;
    private Map<String, String> errors;

    public ValidationErrorResponse(Exception e, Map<String, String> errors, HttpStatus status) {
        this.timestamp = ApplicationTimestamp.getTimestampUTC();
        this.status = status.value() + " " + status.getReasonPhrase();
        this.exception = e.getClass().getSimpleName();
        this.errors = errors;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}