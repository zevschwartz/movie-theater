package com.jpmc.theater;

import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TheaterService {

    @NotNull
    private static final String LINE_SEPARATOR =
            System.getProperty("line.separator") != null ? System.getProperty("line.separator") : "\n";

    final CurrentDateProvider currentDateProvider;

    public TheaterService(CurrentDateProvider currentDateProvider) {
        this.currentDateProvider = currentDateProvider;
    }

    public @NotNull List<Showing> generateMovieData() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, 1);
        Movie turningRed = new Movie("Turning Red", Duration.ofMinutes(85), 11, 0);
        Movie theBatMan = new Movie("The Batman", Duration.ofMinutes(95), 9, 0);
        return List.of(
                new Showing(turningRed, 1, LocalDateTime.of(currentDateProvider.currentDate(), LocalTime.of(9, 0))),
                new Showing(spiderMan, 2, LocalDateTime.of(currentDateProvider.currentDate(), LocalTime.of(11, 0))),
                new Showing(theBatMan, 3, LocalDateTime.of(currentDateProvider.currentDate(), LocalTime.of(12, 50))),
                new Showing(turningRed, 4, LocalDateTime.of(currentDateProvider.currentDate(), LocalTime.of(14, 30))),
                new Showing(spiderMan, 5, LocalDateTime.of(currentDateProvider.currentDate(), LocalTime.of(16, 10))),
                new Showing(theBatMan, 6, LocalDateTime.of(currentDateProvider.currentDate(), LocalTime.of(17, 50))),
                new Showing(turningRed, 7, LocalDateTime.of(currentDateProvider.currentDate(), LocalTime.of(19, 30))),
                new Showing(spiderMan, 8, LocalDateTime.of(currentDateProvider.currentDate(), LocalTime.of(21, 10))),
                new Showing(theBatMan, 9, LocalDateTime.of(currentDateProvider.currentDate(), LocalTime.of(23, 0)))
        );
    }


    public @NotNull String getScheduleFormatted(@NotNull Theater theater) {
        StringBuilder builder = new StringBuilder()
                .append(currentDateProvider.currentDate()).append(LINE_SEPARATOR)
                .append("===================================================").append(LINE_SEPARATOR);

        theater.schedule().forEach(s ->
                builder.append(s.sequenceOfTheDay())
                        .append(": ").append(s.showStartTime())
                        .append(" ").append(s.movie().title())
                        .append(" ").append(humanReadableFormat(s.movie().runningTime()))
                        .append(" $").append(s.getMovieFee())
                        .append(LINE_SEPARATOR)
        );
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
