package com.jpmc.theater;

import com.jpmc.theater.model.Showing;
import com.jpmc.theater.model.Theater;
import com.jpmc.theater.pricing.Discount;
import com.jpmc.theater.pricing.DiscountRule;
import com.jpmc.theater.pricing.MovieDiscountRule;
import com.jpmc.theater.service.CurrentDateProviderImpl;
import com.jpmc.theater.json.DurationAdapter;
import com.jpmc.theater.json.LocalDateAdapter;
import com.jpmc.theater.json.LocalDateTimeAdapter;
import com.jpmc.theater.service.TheaterService;
import com.squareup.moshi.Moshi;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.BiPredicate;

public class Application {
    public static void main(String[] args) {
        var currentDateProvider = CurrentDateProviderImpl.getInstance();
        TheaterService theaterService = new TheaterService(currentDateProvider);
        Theater theater = new Theater(theaterService.getShowings(), getDiscountRules());
        System.out.println(theaterService.getScheduleFormatted(theater));

        System.out.println("json version");
        Moshi moshi = getMoshi();

        var adapter = moshi.adapter(TheaterSchedule.class);

        System.out.println(adapter.toJson(theaterService.getTheaterSchedule(theater)));

    }

    @NotNull
    static Moshi getMoshi() {
        return new Moshi.Builder()
                .add(new DurationAdapter())
                .add(new LocalDateAdapter())
                .add(new LocalDateTimeAdapter())
                .build();
    }


    public static @NotNull List<DiscountRule> getDiscountRules() {
        final BiPredicate<Showing, Integer> specialMovieOne = (show, __) -> show.movie().specialCode() == 1;
        BiPredicate<Showing, Integer> firstMoviePredicate = (__, sequence) -> sequence == 1;
        BiPredicate<Showing, Integer> secondMoviePredicate = (__, sequence) -> sequence == 2;
        BiPredicate<Showing, Integer> elevenAndFourPredicate = (show, __) -> show.showStartTime().getHour() >= 11 && show.showStartTime().getHour() <= 16;
        BiPredicate<Showing, Integer> seventhOfMonthPredicate = (show, __) -> show.showStartTime().getDayOfMonth() == 7;

        var specialMovieDiscount = new MovieDiscountRule(specialMovieOne, Discount.ofPercentage(20)) {
        };
        var firstMovieDiscount = new MovieDiscountRule(firstMoviePredicate, Discount.ofFixed(3));
        var secondMovieDiscount = new MovieDiscountRule(secondMoviePredicate, Discount.ofFixed(2));
        var elevenAndFourhDiscount = new MovieDiscountRule(elevenAndFourPredicate, Discount.ofPercentage(25));
        var seventhOfMonthDiscount = new MovieDiscountRule(seventhOfMonthPredicate, Discount.ofFixed(1));

        return List.of(specialMovieDiscount, firstMovieDiscount, secondMovieDiscount, elevenAndFourhDiscount, seventhOfMonthDiscount);
    }

}
