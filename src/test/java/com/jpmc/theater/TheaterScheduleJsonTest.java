package com.jpmc.theater;

import com.jpmc.theater.json.MoshiConfig;
import com.jpmc.theater.service.TheaterShow;
import com.jpmc.theater.service.TheaterSchedule;
import com.squareup.moshi.Moshi;
import org.intellij.lang.annotations.Language;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TheaterScheduleJsonTest {

    private final Moshi moshi = MoshiConfig.getMoshi();

    @Test
    void shouldSerializeCorrectly() throws JSONException {
        @Language("JSON") var expected = """
                {
                  "currentDate": "20220524",
                  "theaterShows": [
                    {
                      "index": 1,
                      "startTime": "2022-05-24T09:00:00",
                      "title": "Turning Red",
                      "runningTime": "PT1H25M",
                      "price": 11.0,
                      "finalPrice": 11.0
                    }
                  ]
                }""";

        TheaterShow turning_red = new TheaterShow(1,
                LocalDateTime.of(2022, Month.MAY, 24, 9, 0),
                "Turning Red",
                Duration.ofMinutes(85),
                11.0, 11.0);

        var theaterSchedule = new TheaterSchedule(LocalDate.of(2022, Month.MAY, 24),
                List.of(turning_red));

        var jsonOutput = moshi.adapter(TheaterSchedule.class).toJson(theaterSchedule);

        JSONAssert.assertEquals(expected, jsonOutput, true);
    }

    @Test
    void shouldDeserializeCorrectly() throws IOException {
        @Language("JSON") var json = """
                {
                  "currentDate": "20220524",
                  "theaterShows": [
                    {
                      "index": 1,
                      "startTime": "2022-05-24T09:00:00",
                      "title": "Turning Red",
                      "runningTime": "PT1H25M",
                      "price": 11.0,
                      "finalPrice": 11.0
                    }
                  ]
                }""";

        TheaterShow turning_red = new TheaterShow(1,
                LocalDateTime.of(2022, Month.MAY, 24, 9, 0),
                "Turning Red",
                Duration.ofMinutes(85),
                11.0, 11.0);

        var expected = new TheaterSchedule(LocalDate.of(2022, Month.MAY, 24),
                List.of(turning_red));

        var deserializedJson = moshi.adapter(TheaterSchedule.class).fromJson(json);

        assertEquals(expected, deserializedJson, "should serialize properly");
    }

}
