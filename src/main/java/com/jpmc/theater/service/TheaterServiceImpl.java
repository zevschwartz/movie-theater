package com.jpmc.theater.service;

import com.jpmc.theater.model.Movie;
import com.jpmc.theater.model.Showing;
import com.jpmc.theater.model.Theater;
import com.jpmc.theater.pricing.Discount;
import com.jpmc.theater.pricing.DiscountRule;
import com.jpmc.theater.pricing.MovieDiscountRule;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.IntStream;

public class TheaterServiceImpl implements TheaterService {

    final CurrentDateProvider currentDateProvider;

    public TheaterServiceImpl(CurrentDateProvider currentDateProvider) {
        this.currentDateProvider = currentDateProvider;
    }

    @Override
    public @NotNull TheaterSchedule getTheaterSchedule() {
        final @NotNull Theater theater = new Theater(getShowings(), getDiscountRules());
        List<ShowDetail> showDetails = IntStream.range(0, theater.schedule().size())
                .boxed()
                .map(index -> {
                    var showing = theater.schedule().get(index);
                    return new ShowDetail(index + 1,
                            showing.showStartTime(),
                            showing.movie().title(),
                            showing.movie().runningTime(),
                            showing.getMovieFee(),
                            theater.calculateTicketPriceForSequence(index+1));
                })
                .toList();

        return new TheaterSchedule(currentDateProvider.currentDate(), showDetails);
    }

    @NotNull List<DiscountRule> getDiscountRules() {
        final BiPredicate<Showing, Integer> specialMovieOne = (show, __) -> show.movie().specialCode() == 1;
        BiPredicate<Showing, Integer> firstMoviePredicate = (__, sequence) -> sequence == 1;
        BiPredicate<Showing, Integer> secondMoviePredicate = (__, sequence) -> sequence == 2;
        BiPredicate<Showing, Integer> elevenAndFourPredicate = (show, __) -> show.showStartTime().getHour() >= 11 && show.showStartTime().getHour() <= 16;
        BiPredicate<Showing, Integer> seventhOfMonthPredicate = (show, __) -> show.showStartTime().getDayOfMonth() == 7;

        var specialMovieDiscount = new MovieDiscountRule(specialMovieOne, Discount.ofPercentage(20)) {
        };
        var firstMovieDiscount = new MovieDiscountRule(firstMoviePredicate, Discount.ofFixed(3));
        var secondMovieDiscount = new MovieDiscountRule(secondMoviePredicate, Discount.ofFixed(2));
        var elevenAndFourthDiscount = new MovieDiscountRule(elevenAndFourPredicate, Discount.ofPercentage(25));
        var seventhOfMonthDiscount = new MovieDiscountRule(seventhOfMonthPredicate, Discount.ofFixed(1));

        return List.of(specialMovieDiscount, firstMovieDiscount, secondMovieDiscount, elevenAndFourthDiscount, seventhOfMonthDiscount);
    }


    @NotNull List<Showing> getShowings() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, 1);
        Movie turningRed = new Movie("Turning Red", Duration.ofMinutes(85), 11, 0);
        Movie theBatMan = new Movie("The Batman", Duration.ofMinutes(95), 9, 0);
        return List.of(
                new Showing(turningRed, LocalDateTime.of(currentDateProvider.currentDate(), LocalTime.of(9, 0))),
                new Showing(spiderMan, LocalDateTime.of(currentDateProvider.currentDate(), LocalTime.of(11, 0))),
                new Showing(theBatMan, LocalDateTime.of(currentDateProvider.currentDate(), LocalTime.of(12, 50))),
                new Showing(turningRed, LocalDateTime.of(currentDateProvider.currentDate(), LocalTime.of(14, 30))),
                new Showing(spiderMan, LocalDateTime.of(currentDateProvider.currentDate(), LocalTime.of(16, 10))),
                new Showing(theBatMan, LocalDateTime.of(currentDateProvider.currentDate(), LocalTime.of(17, 50))),
                new Showing(turningRed, LocalDateTime.of(currentDateProvider.currentDate(), LocalTime.of(19, 30))),
                new Showing(spiderMan, LocalDateTime.of(currentDateProvider.currentDate(), LocalTime.of(21, 10))),
                new Showing(theBatMan, LocalDateTime.of(currentDateProvider.currentDate(), LocalTime.of(23, 0)))
        );
    }

}
