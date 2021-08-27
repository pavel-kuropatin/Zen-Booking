package com.kuropatin.bookingapp.model.response;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

@Getter
@Setter
public class SuccessfulResponse {

    private Timestamp timestamp;
    private String status;
    private String message;

    public SuccessfulResponse(Timestamp timestamp, String message) {
        this.timestamp = timestamp;
        this.status = HttpStatus.OK.value() + " " + HttpStatus.OK.getReasonPhrase();
        this.message = message;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}