package com.jpmc.theater.pricing;

import com.jpmc.theater.model.Showing;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;

public class DiscountRule implements BiFunction<Showing, Integer, Double> {
    private final @NotNull BiPredicate<Showing, Integer> showingPredicate;
    private final @NotNull DiscountType discountType;

    public DiscountRule(@NotNull BiPredicate<Showing, Integer> showingPredicate, @NotNull DiscountType discountType) {
        this.showingPredicate = showingPredicate;
        this.discountType = discountType;
    }

    @Override
    public Double apply(Showing showing, Integer sequenceInDay) {
        if(!showingPredicate.test(showing, sequenceInDay)) {
            return 0.0;
        }

        return switch (discountType) {
            case FixedDiscountType fixed -> fixed.getFixedValue();
            case PercentDiscountType percent -> showing.getMovieFee() * percent.getPercentValue() / 100.0;
        };
    }
}
