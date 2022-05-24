package com.jpmc.theater.service;

import com.jpmc.theater.model.Customer;
import com.jpmc.theater.model.Theater;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Integrations Tests")
class TheaterServiceImplntegrationTest {

    private final TheaterServiceImpl theaterService = new TheaterServiceImpl(CurrentDateProviderImpl.getInstance());

    @Nested
    @DisplayName("Pricing Rules on Reservations IT")
    class ReservationTests {

        private final Theater theater = new Theater(theaterService.getShowings(), theaterService.getDiscountRules());

        @Test
        void checkSpiderManMovieSpecialPercentOffPrice() {
            var reservation = theater.reserveWithSequence(new Customer("id", "name"), 8, 4);

            assertEquals("Spider-Man: No Way Home", reservation.showing().movie().title());
            assertEquals(12.5, reservation.showing().getMovieFee());

            assertEquals(40.0, theater.calculateTotalPriceForReservation(reservation));
        }

        @Test
        void checkSpiderManMovieElevenToFourPercentOffPrice() {
            var reservation = theater.reserveWithSequence(new Customer("id", "name"), 2, 4);

            assertEquals("Spider-Man: No Way Home", reservation.showing().movie().title());
            assertEquals(12.5, reservation.showing().getMovieFee());

            assertEquals(37.5, theater.calculateTotalPriceForReservation(reservation));
        }

        @Test
        void checkFirstMovieDiscount() {
            var reservation = theater.reserveWithSequence(new Customer("id", "name"), 1, 2);

            assertEquals("Turning Red", reservation.showing().movie().title());
            assertEquals(11, reservation.showing().getMovieFee());

            assertEquals(16, theater.calculateTotalPriceForReservation(reservation));
        }

        @Test
        void checkRegularPriceMovieNoDiscount() {
            var reservation = theater.reserveWithSequence(new Customer("id", "name"), 6, 10);

            assertEquals("The Batman", reservation.showing().movie().title());
            assertEquals(9, reservation.showing().getMovieFee());

            assertEquals(90, theater.calculateTotalPriceForReservation(reservation));
        }
    }
}