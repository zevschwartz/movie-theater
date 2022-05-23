package com.jpmc.theater;

import org.jetbrains.annotations.NotNull;

import java.util.List;

record Theater(List<Showing> schedule, @NotNull List<DiscountRule> discountRules) {

    Theater(List<Showing> schedule) {
        this(schedule, List.of());
    }

    public double calculateTicketPriceForShowing(int sequenceInDay) {
        if(sequenceInDay < 0 || sequenceInDay > schedule.size()) {
            throw new IllegalArgumentException("Invalid range sequence");
        }

        var showing = schedule.get(sequenceInDay - 1);

        var discount = discountRules.stream()
                .mapToDouble(r -> r.calculateTotalDiscount(showing, sequenceInDay))
                .max()
                .orElse(0.0);

        return showing.getMovieFee() - discount;
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
