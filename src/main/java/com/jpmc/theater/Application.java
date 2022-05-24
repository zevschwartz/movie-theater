package com.jpmc.theater;

import com.jpmc.theater.model.Theater;
import com.jpmc.theater.service.CurrentDateProviderImpl;
import com.jpmc.theater.service.TheaterService;

public class Application {
    public static void main(String[] args) {
        var currentDateProvider = CurrentDateProviderImpl.getInstance();
        TheaterService theaterService = new TheaterService(currentDateProvider);
        Theater theater = theaterService.getTheater();
        System.out.println(theaterService.getScheduleFormatted(theater));
    }
}
