package com.jpmc.theater.model;

import java.time.LocalDateTime;

public record Showing(Movie movie, LocalDateTime showStartTime) {
    public double getMovieFee() {
        return movie.ticketPrice();
    }
}
