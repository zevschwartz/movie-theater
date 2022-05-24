package com.jpmc.theater.service;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

@FunctionalInterface
public interface CurrentDateProvider {
    @NotNull LocalDate currentDate();
}

