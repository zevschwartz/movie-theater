package com.jpmc.theater.model;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ShowingTest {

    @Test
    void shouldReturnMovieFee() {
        var movie = new Movie("spiderman", Duration.ofMinutes(90), 11, 1);

        var subject = new Showing(movie, LocalDateTime.now());

        assertEquals(movie.ticketPrice(), subject.getMovieFee(), "movie price should be same as showing movie fee");
        assertEquals(11, subject.getMovieFee(), "movie fee should be 11");
    }
}