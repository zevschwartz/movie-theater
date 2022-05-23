package com.jpmc.theater;

import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TheaterService {
    final LocalDateProvider localDateProvider;

    public TheaterService(LocalDateProvider localDatelocalDateProvider) {
        this.localDateProvider = localDatelocalDateProvider;
    }

    public List<Showing> generateMovieData() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, 1);
        Movie turningRed = new Movie("Turning Red", Duration.ofMinutes(85), 11, 0);
        Movie theBatMan = new Movie("The Batman", Duration.ofMinutes(95), 9, 0);
        return List.of(
                new Showing(turningRed, 1, LocalDateTime.of(localDateProvider.currentDate(), LocalTime.of(9, 0))),
                new Showing(spiderMan, 2, LocalDateTime.of(localDateProvider.currentDate(), LocalTime.of(11, 0))),
                new Showing(theBatMan, 3, LocalDateTime.of(localDateProvider.currentDate(), LocalTime.of(12, 50))),
                new Showing(turningRed, 4, LocalDateTime.of(localDateProvider.currentDate(), LocalTime.of(14, 30))),
                new Showing(spiderMan, 5, LocalDateTime.of(localDateProvider.currentDate(), LocalTime.of(16, 10))),
                new Showing(theBatMan, 6, LocalDateTime.of(localDateProvider.currentDate(), LocalTime.of(17, 50))),
                new Showing(turningRed, 7, LocalDateTime.of(localDateProvider.currentDate(), LocalTime.of(19, 30))),
                new Showing(spiderMan, 8, LocalDateTime.of(localDateProvider.currentDate(), LocalTime.of(21, 10))),
                new Showing(theBatMan, 9, LocalDateTime.of(localDateProvider.currentDate(), LocalTime.of(23, 0)))
        );
    }


    public void printSchedule(Theater theater) {
        System.out.println(localDateProvider.currentDate());
        System.out.println("===================================================");
        theater.schedule().forEach(s ->
                System.out.println(s.getSequenceOfTheDay() + ": " + s.showStartTime() + " " + s.movie().title() + " " + humanReadableFormat(s.movie().runningTime()) + " $" + s.getMovieFee())
        );
        System.out.println("===================================================");
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
        }
        else {
            return "s";
        }
    }
}
