package com.jpmc.theater.pricing;

public abstract sealed class Discount permits FixedDiscount, PercentDiscount {

    public static Discount ofFixed(double fixedDiscount) {
        return new FixedDiscount(fixedDiscount);
    }
    public static Discount ofPercentage(double percentValue) {
        return new PercentDiscount(percentValue);
    }
}
