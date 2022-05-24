package com.jpmc.theater.json;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeAdapter {
    @ToJson
    String fromLocalDateTime(LocalDateTime dateTime) {
        return DateTimeFormatter.ISO_DATE_TIME.format(dateTime);
    }


    @FromJson
    LocalDateTime fromIsoString(String isoDate) {
        return LocalDateTime.parse(isoDate, DateTimeFormatter.ISO_DATE_TIME);
    }
}
