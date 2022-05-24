package com.jpmc.theater.pricing;

import com.jpmc.theater.model.Showing;

@FunctionalInterface
public interface DiscountRule {
    double calculateTotalDiscount(Showing showing, int sequenceInDay);
}

