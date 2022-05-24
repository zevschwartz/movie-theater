package com.jpmc.theater;

import com.jpmc.theater.json.DurationAdapter;
import com.jpmc.theater.json.LocalDateAdapter;
import com.jpmc.theater.json.LocalDateTimeAdapter;
import com.jpmc.theater.service.CurrentDateProviderImpl;
import com.jpmc.theater.service.TheaterSchedule;
import com.jpmc.theater.service.TheaterService;
import com.jpmc.theater.service.TheaterServiceImpl;
import com.squareup.moshi.Moshi;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

class Application {

    @NotNull
    private static final String LINE_SEPARATOR =
            System.getProperty("line.separator") != null ? System.getProperty("line.separator") : "\n";

    private final @NotNull TheaterService theaterService;
    private final @NotNull Moshi moshi;

    Application(@NotNull Moshi moshi, @NotNull TheaterService theaterService) {
        this.theaterService = theaterService;
        this.moshi = moshi;
    }

    public static void main(String[] args) {
        var currentDateProvider = CurrentDateProviderImpl.getInstance();
        var theaterService = new TheaterServiceImpl(currentDateProvider);
        var moshi = getMoshi();

        var application = new Application(moshi, theaterService);

        System.out.println("\n\n=== string formatted version ===");

        System.out.println(application.getTheaterScheduleFormatted());

        System.out.println("\n\n=== json formatted version ===");

        System.out.println(application.getTheaterScheduleAsJson());

    }

    @NotNull
    public static Moshi getMoshi() {
        return new Moshi.Builder()
                .add(new DurationAdapter())
                .add(new LocalDateAdapter())
                .add(new LocalDateTimeAdapter())
                .build();
    }

    String getTheaterScheduleFormatted() {
        var theaterSchedule = theaterService.getTheaterSchedule();

        return theaterScheduleFormatted(theaterSchedule);
    }

    String getTheaterScheduleAsJson() {
        var theaterSchedule = theaterService.getTheaterSchedule();

        return moshi.adapter(TheaterSchedule.class).toJson(theaterSchedule);
    }

    private @NotNull String theaterScheduleFormatted(@NotNull TheaterSchedule theaterSchedule) {
        StringBuilder builder = new StringBuilder()
                .append(theaterSchedule.currentDate()).append(LINE_SEPARATOR)
                .append("===================================================").append(LINE_SEPARATOR);

        theaterSchedule.showDetails().forEach(showDetail -> builder.append(showDetail.index())
                .append(": ").append(showDetail.startTime())
                .append(" ").append(showDetail.title())
                .append(" ").append(humanReadableFormat(showDetail.runningTime()))
                .append(" $").append(showDetail.price())
                .append(LINE_SEPARATOR));

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
