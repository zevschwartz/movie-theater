package com.jpmc.theater.service;

import java.time.Duration;
import java.time.LocalDateTime;

public record TheaterShow(int index, LocalDateTime startTime, String title, Duration runningTime, double price,
                          double finalPrice) {
}
