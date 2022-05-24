package com.jpmc.theater.pricing;

final public class FixedDiscount extends Discount {
    private final double fixedValue;

    public FixedDiscount(double value) {
        this.fixedValue = value;
    }

    public double getFixedValue() {
        return fixedValue;
    }
}
