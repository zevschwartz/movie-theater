package com.jpmc.theater;

import java.util.Arrays;
import java.util.List;

@FunctionalInterface
public interface DiscountRule {
    double calculateTotalDiscount(Showing showing, int sequenceInDay);
}


record SpecialMovieDiscountRule(int percentOff) implements DiscountRule {
    private final static int MOVIE_CODE_SPECIAL = 1;

    SpecialMovieDiscountRule {
        if (percentOff < 0 || percentOff > 100) {
            throw new IllegalStateException("percent off must be between 0 and 100");
        }
    }

    @Override
    public double calculateTotalDiscount(Showing showing, int sequenceInDay) {
        double decimalFromPercent = percentOff / 100.0;

        return showing.movie().specialCode() == MOVIE_CODE_SPECIAL ? showing.getMovieFee() * decimalFromPercent : 0;
    }
}


record SequenceDiscountRule(List<Integer> dollarsOff) implements DiscountRule {
    SequenceDiscountRule(Integer... dollarsOff) {
        this(Arrays.asList(dollarsOff));
    }

    @Override
    public double calculateTotalDiscount(Showing showing, int sequenceInDay) {
        if (sequenceInDay < 1 || sequenceInDay > dollarsOff.size()) {
            return 0;
        }

        return dollarsOff.get(sequenceInDay - 1);
    }

}