package com.jpmc.theater;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.IntStream;

record Theater(@NotNull List<@NotNull Showing> schedule, @NotNull List<@NotNull DiscountRule> discountRules) {

    Theater(List<Showing> schedule) {
        this(schedule, List.of());
    }

    public double calculateTicketPriceForShowing(int sequenceInDay) {
        if (sequenceInDay < 0 || sequenceInDay > schedule.size()) {
            throw new IllegalArgumentException("Invalid range sequence");
        }

        var showing = getShowingForSequence(sequenceInDay);

        var discount = discountRules.stream()
                .mapToDouble(r -> r.calculateTotalDiscount(showing, sequenceInDay))
                .max()
                .orElse(0.0);

        return showing.movie().ticketPrice() - discount;
    }


    public @NotNull Reservation reserve(@NotNull Customer customer, int sequence, int howManyTickets) {
        Showing showing = getShowingForSequence(sequence);

        return new Reservation(customer, showing, howManyTickets);
    }


    public double getPriceForReservation(@NotNull Reservation reservation) {
        var reservedShowing = reservation.showing();
        var showingSequence = findSequenceForShowing(reservedShowing);

        return calculateTicketPriceForShowing(showingSequence) * reservation.audienceCount();
    }

    @NotNull
    private Showing getShowingForSequence(int sequence) {
        if(sequence < 1 || sequence > schedule.size()){
            throw new IllegalStateException("not able to find any showing for given sequence " + sequence);
        }

        @NotNull Showing showing = schedule.get(sequence - 1);
        return showing;
    }

    private int findSequenceForShowing(@NotNull Showing reservedShowing) {
        return IntStream.range(0, schedule.size())
                .boxed()
                .filter(index -> {
                    var scheduledShowing = schedule.get(index);
                    return scheduledShowing.equals(reservedShowing);
                })
                .findFirst()
                .map(index -> index + 1)
                .orElseThrow(() ->
                        new IllegalStateException("invalid showing in this reservation")
                );
    }

}
