package com.jpmc.theater.model;

import org.jetbrains.annotations.NotNull;

public record Customer(@NotNull String id, @NotNull String name) {
}