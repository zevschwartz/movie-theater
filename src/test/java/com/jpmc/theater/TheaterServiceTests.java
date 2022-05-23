package com.jpmc.theater;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TheaterServiceTests {
    @Test
    void totalFeeForCustomer() {
        var theaterService = new TheaterService(LocalDateProvider.singleton());
        var theater = new Theater(theaterService.generateMovieData());

        Customer john = new Customer("id-12345", "John Doe");

        Reservation reservation = theater.reserve(john, 2, 4);
//        System.out.println("You have to pay " + reservation.getTotalFee());
        assertEquals(reservation.totalFee(), 50);
    }

    @Test
    void printMovieSchedule() {
        var theaterService = new TheaterService(LocalDateProvider.singleton());
        var theater = new Theater(theaterService.generateMovieData());

        theaterService.printSchedule(theater);
    }
}
