package com.jpmc.theater.model;

import org.jetbrains.annotations.NotNull;

public record Reservation(@NotNull Customer customer, @NotNull Showing showing, int audienceCount) {
}