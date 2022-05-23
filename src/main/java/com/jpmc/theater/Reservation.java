package com.jpmc.theater;

import org.jetbrains.annotations.NotNull;

record Reservation(@NotNull Customer customer, @NotNull Showing showing, int audienceCount) {
}