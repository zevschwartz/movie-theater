package com.jpmc.theater.pricing;

final class FixedDiscountType extends DiscountType {
    private final double fixedValue;

    public FixedDiscountType(double value) {
        this.fixedValue = value;
    }

    public double getFixedValue() {
        return fixedValue;
    }
}
