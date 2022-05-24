package com.jpmc.theater;

import com.jpmc.theater.model.Customer;
import com.jpmc.theater.model.Theater;
import com.jpmc.theater.service.CurrentDateProviderImpl;
import com.jpmc.theater.service.TheaterService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationIntegrationTest {

    private final TheaterService theaterService = new TheaterService(CurrentDateProviderImpl.getInstance());
    private final Theater subject = new Theater(theaterService.getShowings(), Application.getDiscountRules());

    @Test
    void checkSpiderManMovieSpecialPercentOffPrice() {
        var reservation = subject.reserveWithSequence(new Customer("id", "name"), 8, 4);

        assertEquals("Spider-Man: No Way Home", reservation.showing().movie().title());
        assertEquals(12.5, reservation.showing().getMovieFee());

        assertEquals(40.0, subject.calculateTicketPriceForReservation(reservation));
    }

    @Test
    void checkSpiderManMovieElevenToFourPercentOffPrice() {
        var reservation = subject.reserveWithSequence(new Customer("id", "name"), 2, 4);

        assertEquals("Spider-Man: No Way Home", reservation.showing().movie().title());
        assertEquals(12.5, reservation.showing().getMovieFee());

        assertEquals(37.5, subject.calculateTicketPriceForReservation(reservation));
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