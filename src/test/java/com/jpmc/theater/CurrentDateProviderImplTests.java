package com.jpmc.theater;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class CurrentDateProviderImplTests {
    @Test
    void shouldReturnCorrectDate() {
        Assertions.assertEquals(LocalDate.now(),
                CurrentDateProviderImpl.getInstance().currentDate(),
                "Current date should be accurate");
    }

    @Test
    void shouldReturnAlwaysSameInstance() {
        var instance1 = CurrentDateProviderImpl.getInstance();
        var instance2 = CurrentDateProviderImpl.getInstance();

        Assertions.assertSame(instance1, instance2, "singleton should always return the same instance");
    }
}
