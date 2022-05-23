package com.jpmc.theater;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TheaterServiceTests {
    @Test
    void shouldGetCorrectReservationWhenTheaterSetupCorrectly() {
        var theaterService = new TheaterService(() -> LocalDate.of(2022, Month.MAY, 22));
        var theater = new Theater(theaterService.generateMovieData());

        Customer john = new Customer("id-12345", "John Doe");

        Reservation reservation = theater.reserve(john, 2, 4);
//        System.out.println("You have to pay " + reservation.getTotalFee());
        assertEquals(reservation.totalFee(), 50);
    }

    @Test
    void shouldGetPrettyScheduleWhenRequested() {
        var theaterService = new TheaterService(() -> LocalDate.of(2022, Month.MAY, 22));
        var theater = new Theater(theaterService.generateMovieData());

        var expected = """
                2022-05-22
                ===================================================
                1: 2022-05-22T09:00 Turning Red (1 hour 25 minutes) $11.0
                2: 2022-05-22T11:00 Spider-Man: No Way Home (1 hour 30 minutes) $12.5
                3: 2022-05-22T12:50 The Batman (1 hour 35 minutes) $9.0
                4: 2022-05-22T14:30 Turning Red (1 hour 25 minutes) $11.0
                5: 2022-05-22T16:10 Spider-Man: No Way Home (1 hour 30 minutes) $12.5
                6: 2022-05-22T17:50 The Batman (1 hour 35 minutes) $9.0
                7: 2022-05-22T19:30 Turning Red (1 hour 25 minutes) $11.0
                8: 2022-05-22T21:10 Spider-Man: No Way Home (1 hour 30 minutes) $12.5
                9: 2022-05-22T23:00 The Batman (1 hour 35 minutes) $9.0
                ===================================================
                """;

        assertEquals(expected, theaterService.getScheduleFormatted(theater), "theaterService should return correct string");
    }
}
