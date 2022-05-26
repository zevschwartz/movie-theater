package com.jpmc.theater;

import com.jpmc.theater.json.MoshiConfig;
import com.jpmc.theater.service.TheaterShow;
import com.squareup.moshi.Moshi;
import org.intellij.lang.annotations.Language;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TheaterShowJsonTest {

    private final Moshi moshi = MoshiConfig.getMoshi();

    @Test
    void shouldSerializeCorrectly() throws JSONException {

        @Language("JSON") var expected = """
                {
                  "index": 1,
                  "startTime": "2022-05-24T09:00:00",
                  "title": "Turning Red",
                  "runningTime": "PT1H25M",
                  "price": 11.0,
                  "finalPrice": 10
                }""";

        TheaterShow turning_red = new TheaterShow(1,
                LocalDateTime.of(2022, Month.MAY, 24, 9, 0),
                "Turning Red",
                Duration.ofMinutes(85),
                11.0, 10.0);

        var jsonOutput = moshi.adapter(TheaterShow.class).toJson(turning_red);

        JSONAssert.assertEquals(expected, jsonOutput, true);
    }

    @Test
    void shouldsDeserializeCorrectly() throws IOException {
        @Language("JSON") var json = """
                {
                  "index": 1,
                  "startTime": "2022-05-24T09:00:00",
                  "title": "Turning Red",
                  "runningTime": "PT1H25M",
                  "price": 11,
                  "finalPrice": 10
                }""";

        var expected  = new TheaterShow(1,
                LocalDateTime.of(2022, Month.MAY, 24, 9, 0),
                "Turning Red",
                Duration.ofMinutes(85),
                11.0, 10.0);

        var deserializedJson = moshi.adapter(TheaterShow.class).fromJson(json);

        assertEquals(expected, deserializedJson, "should serialize properly");

    }

}
