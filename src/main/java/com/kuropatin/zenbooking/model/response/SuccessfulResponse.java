package com.kuropatin.zenbooking.model.response;

import com.kuropatin.zenbooking.util.ToStringUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SuccessfulResponse implements Response {

    private Timestamp timestamp;
    private String status;
    private String message;

    public SuccessfulResponse(final Timestamp timestamp, final String message) {
        this.timestamp = timestamp;
        this.status = HttpStatus.OK.value() + " " + HttpStatus.OK.getReasonPhrase();
        this.message = message;
    }

    @Override
    public String toString() {
        return ToStringUtils.toJsonString(this);
    }
}