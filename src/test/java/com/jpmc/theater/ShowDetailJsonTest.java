package com.jpmc.theater;

import com.squareup.moshi.Moshi;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShowDetailJsonTest {

    private final Moshi moshi = Application.getMoshi();

    @Test
    void shouldSerializeCorrectly() {
        var expected = """
                {"index":1,"startTime":"2022-05-24T09:00:00","title":"Turning Red","runningTime":"PT1H25M","price":11.0}""";

        ShowDetail turning_red = new ShowDetail(1,
                LocalDateTime.of(2022, Month.MAY, 24, 9, 0),
                "Turning Red",
                Duration.ofMinutes(85),
                11.0);

        var jsonOutput = moshi.adapter(ShowDetail.class).toJson(turning_red);

        assertEquals(expected, jsonOutput, "moshi needs to deserialize correctly");
    }

    @Test
    void shouldsDeserializeCorrectly() throws IOException {
        var json = """
                {"index":1,"startTime":"2022-05-24T09:00:00","title":"Turning Red","runningTime":"PT1H25M","price":11.0}""";

        var expected  = new ShowDetail(1,
                LocalDateTime.of(2022, Month.MAY, 24, 9, 0),
                "Turning Red",
                Duration.ofMinutes(85),
                11.0);

        var deserializedJson = moshi.adapter(ShowDetail.class).fromJson(json);

        assertEquals(expected, deserializedJson, "should serialize properly");

    }

}
