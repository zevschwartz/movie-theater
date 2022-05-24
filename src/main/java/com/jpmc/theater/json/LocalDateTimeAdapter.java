package com.jpmc.theater.json;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeAdapter {
    @ToJson
    @NotNull
    String fromLocalDateTime(@NotNull LocalDateTime dateTime) {
        return DateTimeFormatter.ISO_DATE_TIME.format(dateTime);
    }


    @FromJson
    @NotNull
    LocalDateTime fromIsoString(@NotNull String isoDate) {
        return LocalDateTime.parse(isoDate, DateTimeFormatter.ISO_DATE_TIME);
    }
}
