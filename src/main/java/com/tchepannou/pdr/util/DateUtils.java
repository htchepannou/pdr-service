package com.tchepannou.pdr.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateUtils {
    public static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private DateUtils (){

    }

    public static LocalDate asLocalDate(java.util.Date date) {
        return asLocalDate(date, ZoneId.systemDefault());
    }

    public static LocalDate asLocalDate(Date date, ZoneId zone) {
        if (date == null) {
            return null;
        }

        if (date instanceof java.sql.Date) {
            return ((java.sql.Date) date).toLocalDate();
        } else {
            return Instant.ofEpochMilli(date.getTime()).atZone(zone).toLocalDate();
        }
    }

    public static LocalDateTime asLocalDateTime(Date date) {
        return asLocalDateTime(date, ZoneId.systemDefault());
    }

    public static LocalDateTime asLocalDateTime(java.util.Date date, ZoneId zone) {
        if (date == null) {
            return null;
        }

        return Instant.ofEpochMilli(date.getTime()).atZone(zone).toLocalDateTime();
    }

    public static Timestamp asTimestamp (LocalDateTime date) {
        return date != null ? new Timestamp(asDate(date, ZoneId.systemDefault()).getTime()) : null;
    }

    public static Date asDate (LocalDateTime date) {
        return asDate(date, ZoneId.systemDefault());
    }

    public static Date asDate (LocalDateTime date, ZoneId zone) {
        if (date == null) {
            return null;
        }

        return Date.from(date.atZone(zone).toInstant());
    }

    public static Timestamp asTimestamp (Date date) {
        if (date == null) {
            return null;
        }
        else if (date instanceof Timestamp) {
            return (Timestamp) date;
        } else {
            return new Timestamp(date.getTime());
        }
    }

    public static String asString (Date date) {
        return date != null ? new SimpleDateFormat(DATETIME_PATTERN).format(date) : null;
    }
}
