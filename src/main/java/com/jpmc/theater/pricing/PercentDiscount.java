package com.jpmc.theater.pricing;

public final class PercentDiscount extends Discount {
    private final double percentValue;

    public PercentDiscount(double value) {
        if (value < 0 || value > 100) {
            throw new IllegalStateException("percent off must be between 0 and 100");
        }
        this.percentValue = value;
    }

    public double getPercentValue() {
        return percentValue;
    }
}
