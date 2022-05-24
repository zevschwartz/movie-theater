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
import java.util.concurrent.TimeUnit;
import java.util.function.BiPredicate;
import java.util.stream.IntStream;

public class TheaterService {

    @NotNull
    private static final String LINE_SEPARATOR =
            System.getProperty("line.separator") != null ? System.getProperty("line.separator") : "\n";

    final CurrentDateProvider currentDateProvider;

    public TheaterService(CurrentDateProvider currentDateProvider) {
        this.currentDateProvider = currentDateProvider;
    }

    public @NotNull Theater getTheater() {
        return new Theater(getShowings(), getDiscountRules());
    }

    private @NotNull List<Showing> getShowings() {
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

    private @NotNull List<DiscountRule> getDiscountRules() {
        final BiPredicate<Showing, Integer> specialMovieOne = (show, __) -> show.movie().specialCode() == 1;
        BiPredicate<Showing, Integer> firstMoviePredicate = (__, sequence) -> sequence == 1;
        BiPredicate<Showing, Integer> secondMoviePredicate = (__, sequence) -> sequence == 2;
        var specialMovieDiscount = new MovieDiscountRule(specialMovieOne, Discount.ofPercentage(20)) {
        };
        var firstMovieDiscount = new MovieDiscountRule(firstMoviePredicate, Discount.ofFixed(3));
        var secondMovieDiscount = new MovieDiscountRule(secondMoviePredicate, Discount.ofFixed(2));

        return List.of(specialMovieDiscount, firstMovieDiscount, secondMovieDiscount);
    }


    public @NotNull String getScheduleFormatted(@NotNull Theater theater) {
        StringBuilder builder = new StringBuilder()
                .append(currentDateProvider.currentDate()).append(LINE_SEPARATOR)
                .append("===================================================").append(LINE_SEPARATOR);

        IntStream.range(0, theater.schedule().size()).forEach(index -> {
            var showing = theater.schedule().get(index);
            builder.append(index + 1)
                    .append(": ").append(showing.showStartTime())
                    .append(" ").append(showing.movie().title())
                    .append(" ").append(humanReadableFormat(showing.movie().runningTime()))
                    .append(" $").append(showing.movie().ticketPrice())
                    .append(LINE_SEPARATOR);
        });
        builder.append("===================================================")
                .append(LINE_SEPARATOR);

        return builder.toString();
    }

    private @NotNull String humanReadableFormat(@NotNull Duration duration) {
        long hour = duration.toHours();
        long remainingMin = duration.toMinutes() - TimeUnit.HOURS.toMinutes(duration.toHours());

        return String.format("(%s hour%s %s minute%s)", hour, handlePlural(hour), remainingMin, handlePlural(remainingMin));
    }

    // (s) postfix should be added to handle plural correctly
    private @NotNull String handlePlural(long value) {
        if (value == 1) {
            return "";
        } else {
            return "s";
        }
    }
}
