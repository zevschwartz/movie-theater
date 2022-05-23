package com.jpmc.theater;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class CurrentDateProviderImplTest {
    @Test
    void shouldReturnCorrectDate() {
        LocalDate expectedDate = LocalDate.now();
        LocalDate currentDateFromSubject = CurrentDateProviderImpl.getInstance().currentDate();

        assertEquals(expectedDate, currentDateFromSubject, "Current date should be accurate");
    }

    @Test
    void shouldReturnAlwaysSameInstance() {
        var instance1 = CurrentDateProviderImpl.getInstance();
        var instance2 = CurrentDateProviderImpl.getInstance();

        assertSame(instance1, instance2, "singleton should always return the same instance");
    }
}
