package com.jpmc.theater;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class TheaterTests {

    @Test
    void totalFee() {
        var customer = new Customer("unused-id", "John Doe");
        var showing = new Showing(
                new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, 1),
                LocalDateTime.now()
        );
        Reservation reservation = new Reservation(customer, showing, 3);

        var subject = new Theater(List.of(showing), List.of());

        Assertions.assertEquals(37.5, subject.getPriceForReservation(reservation), "Reservation fee should calculate correctly");
    }
}
