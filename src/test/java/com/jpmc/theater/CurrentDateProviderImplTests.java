package com.jpmc.theater;

import org.junit.jupiter.api.Test;

public class CurrentDateProviderImplTests {
    @Test
    void makeSureCurrentTime() {
        System.out.println("current time is - " + CurrentDateProviderImpl.getInstance().currentDate());
    }
}
