package com.jpmc.theater.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public record ShowDetail(int index, LocalDateTime startTime, String title, Duration runningTime, double price,
                         double finalPrice) {
}
