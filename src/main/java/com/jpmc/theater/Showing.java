package com.jpmc.theater;

import java.time.LocalDateTime;


record Showing(Movie movie, LocalDateTime showStartTime) {

    public double getMovieFee() {
        return movie.ticketPrice();
    }

}
