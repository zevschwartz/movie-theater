package com.jpmc.theater;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;


record Showing(Movie movie, int sequenceOfTheDay, LocalDateTime showStartTime) {

    public double getMovieFee() {
        return movie.ticketPrice();
    }

}
