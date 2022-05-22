package com.jpmc.theater;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.Objects;


record Movie(@NotNull String title, String description, @NotNull Duration runningTime, double ticketPrice,
             int specialCode) {
    private final static int MOVIE_CODE_SPECIAL = 1;

    public Movie(String title, Duration runningTime, double ticketPrice, int specialCode) {
        this(title, null, runningTime, ticketPrice, specialCode);
    }

    public double calculateTicketPrice(@NotNull Showing showing) {
        return ticketPrice - getDiscount(showing.getSequenceOfTheDay());
    }

    private double getDiscount(int showSequence) {
        double specialDiscount = 0;
        if (MOVIE_CODE_SPECIAL == specialCode) {
            specialDiscount = ticketPrice * 0.2;  // 20% discount for special movie
        }

        double sequenceDiscount = 0;
        if (showSequence == 1) {
            sequenceDiscount = 3; // $3 discount for 1st show
        } else if (showSequence == 2) {

            sequenceDiscount = 2; // $2 discount for 2nd show
        }
//        else {
//            throw new IllegalArgumentException("failed exception");
//        }

        // biggest discount wins
        return Math.max(specialDiscount, sequenceDiscount);
    }

}