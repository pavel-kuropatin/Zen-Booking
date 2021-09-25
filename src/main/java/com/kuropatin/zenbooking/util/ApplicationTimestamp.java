package com.kuropatin.zenbooking.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApplicationTimestamp {

    public static Timestamp getTimestampUTC() {
        return Timestamp.valueOf(LocalDateTime.now());
    }
}