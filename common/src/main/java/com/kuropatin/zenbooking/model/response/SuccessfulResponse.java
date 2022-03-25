package com.kuropatin.zenbooking.model.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

@Getter
@Setter
public class SuccessfulResponse {

    private Timestamp timestamp;
    private String status;
    private String message;

    public SuccessfulResponse(final Timestamp timestamp, final String message) {
        this.timestamp = timestamp;
        this.status = HttpStatus.OK.value() + " " + HttpStatus.OK.getReasonPhrase();
        this.message = message;
    }

    @SneakyThrows
    @Override
    public String toString() {
        return new ObjectMapper().writeValueAsString(this);
    }
}