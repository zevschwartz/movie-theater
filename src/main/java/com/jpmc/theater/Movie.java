package com.jpmc.theater;

import org.jetbrains.annotations.NotNull;

import java.time.Duration;


record Movie(@NotNull String title, String description, @NotNull Duration runningTime, double ticketPrice,
             int specialCode) {

    public Movie(@NotNull String title, @NotNull Duration runningTime, double ticketPrice, int specialCode) {
        this(title, null, runningTime, ticketPrice, specialCode);
    }
}