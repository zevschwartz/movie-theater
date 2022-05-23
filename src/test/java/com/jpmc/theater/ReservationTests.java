package com.jpmc.theater;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReservationTests {

    @Test
    void totalFee() {
        var customer = new Customer("unused-id", "John Doe");
        var showing = new Showing(
                new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, 1),
                LocalDateTime.now()
        );
        assertEquals(37.5, new Reservation(customer, showing, 3).totalFee(), "Reservation fee should calculate correctly");
    }
}
