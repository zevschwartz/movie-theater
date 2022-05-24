package com.jpmc.theater.service;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface TheaterService {
    @NotNull TheaterSchedule getTheaterSchedule();
}
