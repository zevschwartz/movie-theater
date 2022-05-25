package com.jpmc.theater.json;

import com.squareup.moshi.Moshi;
import org.jetbrains.annotations.NotNull;

public final class MoshiConfig {
    private MoshiConfig() {
    }

    @NotNull
    public static Moshi getMoshi() {
        return new Moshi.Builder()
                .add(new DurationAdapter())
                .add(new LocalDateAdapter())
                .add(new LocalDateTimeAdapter())
                .build();
    }
}
