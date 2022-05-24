package com.jpmc.theater;

import com.squareup.moshi.Moshi;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TheaterScheduleJsonTest {

    private final Moshi moshi = Application.getMoshi();

    @Test
    void shouldSerializeCorrectly() {
        var expected = """
                {"currentDate":"20220524","showDetails":[{"index":1,"startTime":"2022-05-24T09:00:00","title":"Turning Red","runningTime":"PT1H25M","price":11.0}]}""";

        ShowDetail turning_red = new ShowDetail(1,
                LocalDateTime.of(2022, Month.MAY, 24, 9, 0),
                "Turning Red",
                Duration.ofMinutes(85),
                11.0);

        var theaterSchedule = new TheaterSchedule(LocalDate.of(2022, Month.MAY, 24),
                List.of(turning_red));

        var jsonOutput = moshi.adapter(TheaterSchedule.class).toJson(theaterSchedule);

        assertEquals(expected, jsonOutput, "moshi needs to deserialize correctly");
    }

    @Test
    void shouldsDeserializeCorrectly() throws IOException {
        var json = """
                {"currentDate":"20220524","showDetails":[{"index":1,"startTime":"2022-05-24T09:00:00","title":"Turning Red","runningTime":"PT1H25M","price":11.0}]}""";

        ShowDetail turning_red = new ShowDetail(1,
                LocalDateTime.of(2022, Month.MAY, 24, 9, 0),
                "Turning Red",
                Duration.ofMinutes(85),
                11.0);

        var expected = new TheaterSchedule(LocalDate.of(2022, Month.MAY, 24),
                List.of(turning_red));

        var deserializedJson = moshi.adapter(TheaterSchedule.class).fromJson(json);

        assertEquals(expected, deserializedJson, "should serialize properly");

    }

}
