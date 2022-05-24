package com.jpmc.theater.service;

import com.jpmc.theater.model.Showing;
import com.jpmc.theater.model.Theater;
import com.jpmc.theater.pricing.DiscountRule;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@FunctionalInterface
public interface TheaterService {
    @NotNull TheaterSchedule getTheaterSchedule();
}
