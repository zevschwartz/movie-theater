package com.jpmc.theater.service;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface TheaterScheduleService {
    @NotNull TheaterSchedule getTheaterSchedule();
}
