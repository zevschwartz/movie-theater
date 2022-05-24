package com.jpmc.theater.json;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.ToJson;

import java.time.Duration;

public class DurationAdapter {
    @ToJson
    String fromDuration(Duration duration) {
        return duration.toString();
    }

    @FromJson
    Duration toDuration(String duration) {
        return Duration.parse(duration);
    }
}
