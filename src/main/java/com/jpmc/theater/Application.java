package com.jpmc.theater;

public class Application {
    public static void main(String[] args) {
        var currentDateProvider = CurrentDateProviderImpl.getInstance();
        TheaterService theaterService = new TheaterService(currentDateProvider);

        Theater theater = new Theater(theaterService.getShowings(), theaterService.getDiscountRules());
        System.out.println(theaterService.getScheduleFormatted(theater));
    }
}
