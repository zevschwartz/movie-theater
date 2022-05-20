package com.jpmc.theater;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;

public class LocalDateProvider {
    private static @Nullable LocalDateProvider instance = null;

    private  LocalDateProvider() {
    }

    /**
     * @return make sure to return singleton instance
     */
    public static @NotNull LocalDateProvider singleton() {
        if (instance == null) {
            instance = new LocalDateProvider();
        }
        return instance;
    }

    public @NotNull LocalDate currentDate() {
        return LocalDate.now();
    }
}
