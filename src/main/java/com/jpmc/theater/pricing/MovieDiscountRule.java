package com.jpmc.theater.pricing;

import com.jpmc.theater.model.Showing;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiPredicate;

public class MovieDiscountRule implements DiscountRule {
    private final @NotNull BiPredicate<Showing, Integer> showingPredicate;
    private final @NotNull Discount discount;

    public MovieDiscountRule(@NotNull BiPredicate<Showing, Integer> showingPredicate, @NotNull Discount discount) {
        this.showingPredicate = showingPredicate;
        this.discount = discount;
    }

    @Override
    public double calculateTotalDiscount(Showing showing, int sequenceInDay) {
        if(!showingPredicate.test(showing, sequenceInDay)) {
            return 0;
        }

        return switch (discount) {
            case FixedDiscount fixed -> fixed.getFixedValue();
            case PercentDiscount percent -> showing.getMovieFee() * percent.getPercentValue() / 100.0;
        };
    }
}
