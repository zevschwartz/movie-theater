package com.jpmc.theater;

import java.time.LocalDateTime;


record Showing(Movie movie, int sequenceOfTheDay, LocalDateTime showStartTime) {
    public boolean isSequence(int sequence) {
        return this.sequenceOfTheDay == sequence;
    }

    public double getMovieFee() {
        return movie.ticketPrice();
    }

    private double calculateFee(int audienceCount) {
        return movie.calculateTicketPrice(this) * audienceCount;
    }
}
