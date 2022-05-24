package com.jpmc.theater;

import java.time.LocalDateTime;

public record ShowDetail(int index, LocalDateTime startTime, String title, java.time.Duration runningTime, double price) {
}
