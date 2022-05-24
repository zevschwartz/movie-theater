package com.jpmc.theater.pricing;

import org.jetbrains.annotations.NotNull;

public abstract sealed class Discount permits FixedDiscount, PercentDiscount {

    public static @NotNull Discount ofFixed(double fixedDiscount) {
        return new FixedDiscount(fixedDiscount);
    }
    public static @NotNull Discount ofPercentage(double percentValue) {
        return new PercentDiscount(percentValue);
    }
}
