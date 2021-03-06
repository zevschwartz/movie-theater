package com.jpmc.theater;

import com.jpmc.theater.model.Movie;
import com.jpmc.theater.model.Showing;
import com.jpmc.theater.model.Theater;
import com.jpmc.theater.pricing.DiscountType;
import com.jpmc.theater.pricing.DiscountRule;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TheaterScheduleServiceImplTest {

    public static final DiscountRule FIRST_DAY_MOVIE_DISCOUNT = new DiscountRule((showing, sequence) -> sequence == 1,
            DiscountType.ofFixed(2));
    public static final DiscountRule SPECIAL_MOVIE_DISCOUNT = new DiscountRule((showing, sequence) -> sequence == 1,
            DiscountType.ofPercentage(30));

    final static DiscountRule SEVENTH_MOVIE_DISCOUNT = new DiscountRule(
            (showing, sequence) -> showing.showStartTime().getDayOfMonth() == 7,
            DiscountType.ofFixed(1));

    @Test
    void shouldCalculateSeventhOfMonthMovieDiscountWhenMax() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 10, 0);
        List<Showing> shows = List.of(new Showing(spiderMan, LocalDateTime.now().withDayOfMonth(7)));

        var discountRules = List.of(SEVENTH_MOVIE_DISCOUNT);

        var theater = new Theater(shows, discountRules);

        var price = theater.calculateTicketPriceForSequence(1);

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

        var price = theater.calculateTicketPriceForSequence(1);

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

        var price = theater.calculateTicketPriceForSequence(1);

        assertEquals(7, price);
    }

}
