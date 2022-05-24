package com.jpmc.theater;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationIntegrationTest {


    private final TheaterService theaterService = new TheaterService(CurrentDateProviderImpl.getInstance());
    private final Theater subject = theaterService.getTheater();

    @Test
    void checkSpiderManMoviePercentOffPrice() {
        var reservation = subject.reserveWithSequence(new Customer("id", "name"), 2, 4);

        assertEquals("Spider-Man: No Way Home", reservation.showing().movie().title());
        assertEquals(12.5, reservation.showing().getMovieFee());

        assertEquals(40, subject.calculateTicketPriceForReservation(reservation));
    }

    @Test
    void checkFirstMovieDiscount() {
        var reservation = subject.reserveWithSequence(new Customer("id", "name"), 1, 2);

        assertEquals("Turning Red", reservation.showing().movie().title());
        assertEquals(11, reservation.showing().getMovieFee());

        assertEquals(16, subject.calculateTicketPriceForReservation(reservation));
    }

    @Test
    void checkRegularPriceMovieNoDiscount() {
        var reservation = subject.reserveWithSequence(new Customer("id", "name"), 6, 10);

        assertEquals("The Batman", reservation.showing().movie().title());
        assertEquals(9, reservation.showing().getMovieFee());

        assertEquals(90, subject.calculateTicketPriceForReservation(reservation));
    }

}