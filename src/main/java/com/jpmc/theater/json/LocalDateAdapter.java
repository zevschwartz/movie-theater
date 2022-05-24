package com.jpmc.theater.json;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter {
    @ToJson
    String fromLocalDate(LocalDate dateTime) {
        return DateTimeFormatter.BASIC_ISO_DATE.format(dateTime);
    }

    @FromJson
    LocalDate fromIsoString(String isoDate) {
        return LocalDate.parse(isoDate, DateTimeFormatter.BASIC_ISO_DATE);
    }
}
