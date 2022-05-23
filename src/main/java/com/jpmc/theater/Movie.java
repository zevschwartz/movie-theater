package com.jpmc.theater;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;


record Movie(@NotNull String title, @Nullable String description, @NotNull Duration runningTime, double ticketPrice,
             int specialCode) {

    public Movie(@NotNull String title, @NotNull Duration runningTime, double ticketPrice, int specialCode) {
        this(title, null, runningTime, ticketPrice, specialCode);
    }
}