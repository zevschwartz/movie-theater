package com.jpmc.theater.model;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

public record Theater(@NotNull List<@NotNull Showing> schedule, @NotNull List<? extends BiFunction<Showing, Integer, Double>> discountRules) {

    public double calculateTicketPriceForSequence(int sequenceInDay) {
        var showing = getShowingForSequence(sequenceInDay);

        var discount = discountRules.stream()
                .mapToDouble(r -> r.apply(showing, sequenceInDay))
                .max()
                .orElse(0.0);

        return showing.getMovieFee() - discount;
    }

    public @NotNull Reservation reserveWithSequence(@NotNull Customer customer, int sequence, int howManyTickets) {
        Showing showing = getShowingForSequence(sequence);

        return new Reservation(customer, showing, howManyTickets);
    }

    public double calculateTotalPriceForReservation(@NotNull Reservation reservation) {
        var reservedShowing = reservation.showing();
        var showingSequence = findSequenceForShowing(reservedShowing);

        return calculateTicketPriceForSequence(showingSequence) * reservation.audienceCount();
    }

    @NotNull
    private Showing getShowingForSequence(int sequence) {
        if (sequence < 1 || sequence > schedule.size()) {
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
