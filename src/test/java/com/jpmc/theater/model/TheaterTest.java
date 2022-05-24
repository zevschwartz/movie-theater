package com.jpmc.theater.model;

import com.jpmc.theater.pricing.DiscountRule;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TheaterTest {


    @ParameterizedTest
    @CsvSource({"1, 45.0", "2, 72.0", "3, 25.0"})
    void shouldGetFinalPriceWhenCalculatingPriceWithValidSequence(int sequence, double expectedPrice) {
        var theater = generateTheaterWithScheduleAndDiscounts();

        assertEquals(expectedPrice,
                theater.calculateTicketPriceForSequence(sequence),
                "Showing fee should calculate correctly");
    }

    @Test
    void shouldCalculateCorrectPriceWhenThereAreMultipleDiscounts() {
        var theater = new Theater(generateShows(),
                List.of(
                        (showing, sequenceInDay) -> sequenceInDay == 1 ? 5 : 0,
                        (showing, sequenceInDay) -> sequenceInDay == 1 ? 8 : 0,
                        (showing, sequenceInDay) -> sequenceInDay == 1 ? 3 : 0
                ));

        assertEquals(42.0,
                theater.calculateTicketPriceForSequence(1),
                "only largest discount should be used");
    }


    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 4})
    void shouldErrorWhenCalculatingPriceWithInvalidSequence(int invalidSequence) {
        var theater = generateTheaterWithScheduleAndDiscounts();

        Assertions.assertThrows(IllegalStateException.class,
                () -> theater.calculateTicketPriceForSequence(invalidSequence),
                "not able to find any showing for given sequence");
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 4})
    void shouldErrorWhenCreatingReservationWithInvalidSequence(int invalidSequence) {
        var theater = generateTheaterWithScheduleAndDiscounts();

        Customer john = new Customer("id-12345", "John Doe");

        Assertions.assertThrows(IllegalStateException.class,
                () -> theater.reserveWithSequence(john, invalidSequence, 4),
                "not able to find any showing for given sequence");

    }

    @Test
    void shouldGetCorrectPriceForReservation() {
        var customer = new Customer("unused-id", "John Doe");
        var showing = new Showing(
                new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, 1),
                LocalDateTime.now()
        );
        Reservation reservation = new Reservation(customer, showing, 3);

        var subject = new Theater(List.of(showing), List.of());

        assertEquals(37.5,
                subject.calculateTotalPriceForReservation(reservation),
                "Reservation fee should calculate correctly");
    }

    @Test
    void shouldGetCorrectReservationAndPriceWhenRetrievingPrice() {
        var theater = generateTheaterWithScheduleAndDiscounts();

        Customer john = new Customer("id-12345", "John Doe");

        Reservation reservation = theater.reserveWithSequence(john, 2, 4);
        assertEquals(theater.schedule().get(1), reservation.showing());
        assertEquals(john, reservation.customer());
        assertEquals(4, reservation.audienceCount());

        double expectedPrice = 4 * 72.0;
        assertEquals(expectedPrice,
                theater.calculateTotalPriceForReservation(reservation),
                "price for reservation is not correct");
    }

    @NotNull
    private Theater generateTheaterWithScheduleAndDiscounts() {
        final List<Showing> schedules = generateShows();

        final List<DiscountRule> discounts = generateDiscounts();


        return new Theater(schedules, discounts);
    }

    @NotNull
    private List<DiscountRule> generateDiscounts() {
        return List.of(
                ((showing, sequenceInDay) -> sequenceInDay == 2 ? showing.getMovieFee() * .10 : 0.0),
                ((showing, sequenceInDay) -> sequenceInDay == 1 ? 5 : 0.0)
        );
    }

    @NotNull
    private List<Showing> generateShows() {
        return List.of(new Showing(
                        new Movie("spiderman", Duration.ofMinutes(90), 50, 0),
                        LocalDateTime.now()
                ),
                new Showing(
                        new Movie("ironman", Duration.ofMinutes(90), 80, 0),
                        LocalDateTime.now()
                ),
                new Showing(
                        new Movie("superman", Duration.ofMinutes(90), 25, 0),
                        LocalDateTime.now()
                )
        );
    }

}
