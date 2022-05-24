package com.jpmc.theater.model;

import org.jetbrains.annotations.NotNull;

import java.time.Duration;

@NotNull
public record Movie(String title, Duration runningTime, double ticketPrice, int specialCode) {
}