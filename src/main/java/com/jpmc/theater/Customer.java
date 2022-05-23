package com.jpmc.theater;

import org.jetbrains.annotations.NotNull;

record Customer(@NotNull String id, @NotNull String name) {
}