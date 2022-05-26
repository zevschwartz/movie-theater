package com.jpmc.theater;

import com.jpmc.theater.json.MoshiConfig;
import com.jpmc.theater.service.CurrentDateProvider;
import com.jpmc.theater.service.TheaterShow;
import com.jpmc.theater.service.TheaterSchedule;
import com.jpmc.theater.service.TheaterScheduleService;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ApplicationTest {

    @Test
    void shouldReturnStringFormatted() {
        CurrentDateProvider currentDateProvider = () -> LocalDate.of(2022, Month.MAY, 22);

        var application = setupApplication();

        var expected = """
                2022-05-22
                ===================================================
                1: 2022-05-22T09:00 Turning Red (1 hour 25 minutes) $11.0
                2: 2022-05-22T11:00 Spider-Man: No Way Home (1 hour 30 minutes) $12.5
                3: 2022-05-22T12:50 The Batman (1 hour 35 minutes) $9.0
                ===================================================
                """;

        assertEquals(expected, application.getTheaterScheduleFormatted(), "formatted string should return correct");

    }


    @Test
    void shouldReturnJsonFormatted() throws JSONException {
        CurrentDateProvider currentDateProvider = () -> LocalDate.of(2022, Month.MAY, 22);

        var application = setupApplication();

        @Language("JSON") var expected = """
                {
                  "currentDate": "20220522",
                  "theaterShows": [
                    {
                      "index": 1,
                      "startTime": "2022-05-22T09:00:00",
                      "title": "Turning Red",
                      "runningTime": "PT1H25M",
                      "price": 11,
                      "finalPrice": 10
                    },
                    {
                      "index": 2,
                      "startTime": "2022-05-22T11:00:00",
                      "title": "Spider-Man: No Way Home",
                      "runningTime": "PT1H30M",
                      "price": 12.5,
                      "finalPrice": 11
                    },
                    {
                      "index": 3,
                      "startTime": "2022-05-22T12:50:00",
                      "title": "The Batman",
                      "runningTime": "PT1H35M",
                      "price": 9,
                      "finalPrice": 9
                    }
                  ]
                }""";

        JSONAssert.assertEquals(expected, application.getTheaterScheduleAsJson(), true);
    }


    @NotNull
    private Application setupApplication() {
        return new Application(MoshiConfig.getMoshi(), theaterServiceStub());
    }

    @NotNull
    private TheaterScheduleService theaterServiceStub() {
        LocalDateTime localDateTime = LocalDateTime.of(2022, Month.MAY, 22, 7, 0);

        var turningRed = new TheaterShow(1, localDateTime.withHour(9).withMinute(0), "Turning Red", Duration.ofMinutes(85), 11, 10);
        var spiderman = new TheaterShow(2, localDateTime.withHour(11).withMinute(0), "Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, 11);
        var batman = new TheaterShow(3, localDateTime.withHour(12).withMinute(50), "The Batman", Duration.ofMinutes(95), 9, 9);

        TheaterSchedule theaterSchedule = new TheaterSchedule(localDateTime.toLocalDate(), List.of(turningRed, spiderman, batman));

        return () -> theaterSchedule;

    }
}
