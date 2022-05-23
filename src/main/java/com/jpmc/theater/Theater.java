package com.jpmc.theater;

import org.jetbrains.annotations.NotNull;

import java.util.List;

record Theater(List<Showing> schedule) {

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
