package com.kuropatin.zenbooking.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApplicationTimeUtils {

    public static Timestamp getTimestampUTC() {
        return Timestamp.valueOf(LocalDateTime.now());
    }

    public static LocalDateTime getTimeUTC() {
        return LocalDateTime.now(ZoneOffset.UTC);
    }

    public static LocalDate getDateUTC() {
        return LocalDate.now(ZoneOffset.UTC);
    }
}