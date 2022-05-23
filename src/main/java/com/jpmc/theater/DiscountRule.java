package com.jpmc.theater;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@FunctionalInterface
public interface DiscountRule {
    double calculateTotalDiscount(Showing showing, int sequenceInDay);
}


class SpecialMoviePercentDiscountRule implements DiscountRule {
    private final int specialMovieCode;
    private final int percentOff;

    public SpecialMoviePercentDiscountRule(int specialMovieCode, int percentOff) {
        if (percentOff < 0 || percentOff > 100) {
            throw new IllegalStateException("percent off must be between 0 and 100");
        }
        this.percentOff = percentOff;
        this.specialMovieCode = specialMovieCode;
    }

    @Override
    public double calculateTotalDiscount(@NotNull Showing showing, int sequenceInDay) {
        double decimalFromPercent = percentOff / 100.0;

        return showing.movie().specialCode() == specialMovieCode ? showing.movie().ticketPrice() * decimalFromPercent : 0;
    }
}


class SequenceDiscountRule implements DiscountRule {
    private final @NotNull List<@NotNull Integer> dollarsOff;

    public SequenceDiscountRule(@NotNull List<@NotNull Integer> dollarsOff) {
        this.dollarsOff = dollarsOff;
    }

    public SequenceDiscountRule(Integer first, Integer... rest) {
        List<Integer> list = new ArrayList<>(rest.length + 1);
        list.add(first);
        list.addAll(Arrays.asList(rest));

        this.dollarsOff = List.copyOf(list);
    }

    @Override
    public double calculateTotalDiscount(Showing showing, int sequenceInDay) {
        if (sequenceInDay < 1 || sequenceInDay > dollarsOff.size()) {
            return 0;
        }

        return dollarsOff.get(sequenceInDay - 1);
    }

}