package com.jpmc.theater.service;

import com.jpmc.theater.model.Movie;
import com.jpmc.theater.model.Showing;
import com.jpmc.theater.model.Theater;
import com.jpmc.theater.pricing.DiscountType;
import com.jpmc.theater.pricing.DiscountRule;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

public class TheaterScheduleServiceImpl implements TheaterScheduleService {

    final CurrentDateProvider currentDateProvider;

    public TheaterScheduleServiceImpl(CurrentDateProvider currentDateProvider) {
        this.currentDateProvider = currentDateProvider;
    }

    @Override
    public @NotNull TheaterSchedule getTheaterSchedule() {
        final @NotNull Theater theater = new Theater(getShowings(), getDiscountRules());
        List<TheaterShow> theaterShows = IntStream.range(0, theater.schedule().size())
                .boxed()
                .map(index -> {
                    var showing = theater.schedule().get(index);
                    return new TheaterShow(index + 1,
                            showing.showStartTime(),
                            showing.movie().title(),
                            showing.movie().runningTime(),
                            showing.getMovieFee(),
                            theater.calculateTicketPriceForSequence(index + 1));
                })
                .toList();

        return new TheaterSchedule(currentDateProvider.currentDate(), theaterShows);
    }

    @NotNull List<BiFunction<Showing, Integer, Double>> getDiscountRules() {
        var specialMovieDiscountRule = new DiscountRule((show, sequence) -> show.movie().specialCode() == 1,
                DiscountType.ofPercentage(20));
        var firstMovieDiscountRule = new DiscountRule((show, sequence) -> sequence == 1,
                DiscountType.ofFixed(3));
        var secondMovieDiscountRule = new DiscountRule((show, sequence) -> sequence == 2,
                DiscountType.ofFixed(2));
        var elevenAndFourthDiscountRule = new DiscountRule(
                (show, sequence) -> show.showStartTime().getHour() >= 11 && show.showStartTime().getHour() <= 16,
                DiscountType.ofPercentage(25));
        var seventhOfMonthDiscountRule = new DiscountRule((show, sequence) -> show.showStartTime().getDayOfMonth() == 7,
                DiscountType.ofFixed(1));

        return List.of(specialMovieDiscountRule,
                firstMovieDiscountRule,
                secondMovieDiscountRule,
                elevenAndFourthDiscountRule,
                seventhOfMonthDiscountRule);
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


    private static boolean isMovieBetweenElevenAndFour(Showing show, Integer sequence) {
        return show.showStartTime().getHour() >= 11 && show.showStartTime().getHour() <= 16;
    }

    private static boolean isMovieInSeventhDayInMonth(Showing show, Integer sequence) {
        return show.showStartTime().getDayOfMonth() == 7;
    }


}
