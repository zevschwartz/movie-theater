package com.jpmc.theater.json;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;

public class DurationAdapter {
    @ToJson
    @NotNull
    String fromDuration(@NotNull Duration duration) {
        return duration.toString();
    }

    @FromJson
    Duration toDuration(@NotNull String duration) {
        return Duration.parse(duration);
    }
}
