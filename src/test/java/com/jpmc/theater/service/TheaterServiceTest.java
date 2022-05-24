package com.jpmc.theater.service;

import com.jpmc.theater.model.Movie;
import com.jpmc.theater.model.Showing;
import com.jpmc.theater.model.Theater;
import com.jpmc.theater.pricing.Discount;
import com.jpmc.theater.pricing.FixedDiscount;
import com.jpmc.theater.pricing.MovieDiscountRule;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TheaterServiceTest {

    public static final MovieDiscountRule FIRST_DAY_MOVIE_DISCOUNT = new MovieDiscountRule((showing, sequence) -> sequence == 1,
            Discount.ofFixed(2));
    public static final MovieDiscountRule SPECIAL_MOVIE_DISCOUNT = new MovieDiscountRule((showing, sequence) -> sequence == 1,
            Discount.ofPercentage(30));

    final static MovieDiscountRule SEVENTH_MOVIE_DISCOUNT = new MovieDiscountRule(
            (showing, sequence) -> showing.showStartTime().getDayOfMonth() == 7,
            Discount.ofFixed(1));

    final TheaterService theaterService = new TheaterService(() -> LocalDate.of(2022, Month.MAY, 22));

    @Test
    void shouldGetPrettyScheduleWhenRequested() {
        var theaterService = new TheaterService(() -> LocalDate.of(2022, Month.MAY, 22));
        var theater = new Theater(theaterService.getShowings(), List.of());

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

    @Test
    void shouldCalculateSeventhOfMonthMovieDiscountWhenMax() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 10, 0);
        List<Showing> shows = List.of(new Showing(spiderMan, LocalDateTime.now().withDayOfMonth(7)));

        var discountRules = List.of(SEVENTH_MOVIE_DISCOUNT);

        var theater = new Theater(shows, discountRules);

        var price = theater.calculateTicketPriceForShowing(1);

        assertEquals(9, price);
    }

    @Test
    void shouldCalculateFirstShowingOfDayMovieDiscountWhenMax() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 10, 0);
        List<Showing> shows = List.of(new Showing(spiderMan, LocalDateTime.now().withDayOfMonth(7)));

        var discountRules = List.of(
                SEVENTH_MOVIE_DISCOUNT,
                FIRST_DAY_MOVIE_DISCOUNT);

        var theater = new Theater(shows, discountRules);

        var price = theater.calculateTicketPriceForShowing(1);

        assertEquals(8, price);
    }

    @Test
    void shouldCalculatePercentageSpecialMovieDiscountWhenMax() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 10, 0);
        List<Showing> shows = List.of(new Showing(spiderMan, LocalDateTime.now().withDayOfMonth(7)));

        var discountRules = List.of(SEVENTH_MOVIE_DISCOUNT,
                FIRST_DAY_MOVIE_DISCOUNT,
                SPECIAL_MOVIE_DISCOUNT);

        var theater = new Theater(shows, discountRules);

        var price = theater.calculateTicketPriceForShowing(1);

        assertEquals(7, price);
    }

}
