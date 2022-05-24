package com.jpmc.theater.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.time.LocalDate;

@FunctionalInterface
public interface CurrentDateProvider {
    LocalDate currentDate();
}

