package com.jpmc.theater.json;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class LocalDateAdapter {
    @ToJson
    @NotNull
    String fromLocalDate(@NotNull LocalDate dateTime) {
        return DateTimeFormatter.BASIC_ISO_DATE.format(dateTime);
    }

    @FromJson
    LocalDate fromIsoString(@NotNull String isoDate) {
        return LocalDate.parse(isoDate, DateTimeFormatter.BASIC_ISO_DATE);
    }
}
