package com.jpmc.theater;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Function;

record Theater(List<Showing> schedule, @NotNull List<Function<Showing, Double>> discountRules) {

    Theater(List<Showing> schedule) {
        this(schedule, List.of());
    }

    public @NotNull Reservation reserve(@NotNull Customer customer, int sequence, int howManyTickets) {
        Showing showing;
        try {
            showing = schedule.get(sequence - 1);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new IllegalStateException("not able to find any showing for given sequence " + sequence);
        }
        return new Reservation(customer, showing, howManyTickets);
    }
}
