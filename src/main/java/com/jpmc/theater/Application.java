package com.jpmc.theater;

public class Application {

    public static void main(String[] args) {
        LocalDateProvider localDateProvider = LocalDateProvider.getInstance();
        TheaterService theaterService = new TheaterService(localDateProvider);

        Theater theater = new Theater(theaterService.generateMovieData());
        System.out.println(theaterService.getScheduleFormatted(theater));
    }


}
