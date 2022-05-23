package com.jpmc.theater;

public class Application {

    public static void main(String[] args) {
        LocalDateProvider localDateProvider = LocalDateProvider.singleton();
        TheaterService theaterService = new TheaterService(localDateProvider);

        Theater theater = new Theater(theaterService.generateMovieData());
        theaterService.printSchedule(theater);
    }


}
