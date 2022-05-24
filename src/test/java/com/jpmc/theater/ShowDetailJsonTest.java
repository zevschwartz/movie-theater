package com.jpmc.theater;

import com.jpmc.theater.service.ShowDetail;
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

public class ShowDetailJsonTest {

    private final Moshi moshi = Application.getMoshi();

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

        ShowDetail turning_red = new ShowDetail(1,
                LocalDateTime.of(2022, Month.MAY, 24, 9, 0),
                "Turning Red",
                Duration.ofMinutes(85),
                11.0, 10.0);

        var jsonOutput = moshi.adapter(ShowDetail.class).toJson(turning_red);

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

        var expected  = new ShowDetail(1,
                LocalDateTime.of(2022, Month.MAY, 24, 9, 0),
                "Turning Red",
                Duration.ofMinutes(85),
                11.0, 10.0);

        var deserializedJson = moshi.adapter(ShowDetail.class).fromJson(json);

        assertEquals(expected, deserializedJson, "should serialize properly");

    }

}
