package com.jpmc.theater.pricing;

import org.jetbrains.annotations.NotNull;

public abstract sealed class DiscountType permits FixedDiscountType, PercentDiscountType {

    public static @NotNull DiscountType ofFixed(double fixedDiscount) {
        return new FixedDiscountType(fixedDiscount);
    }
    public static @NotNull DiscountType ofPercentage(double percentValue) {
        return new PercentDiscountType(percentValue);
    }
}
