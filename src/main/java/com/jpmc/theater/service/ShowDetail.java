package com.jpmc.theater.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public final class ShowDetail {
    private final int index;
    private final LocalDateTime startTime;
    private final String title;
    private final Duration runningTime;
    private final double price;
    private final double finalPrice;

    public ShowDetail(int index, LocalDateTime startTime, String title, Duration runningTime, double price, double finalPrice) {
        this.index = index;
        this.startTime = startTime;
        this.title = title;
        this.runningTime = runningTime;
        this.price = price;
        this.finalPrice = finalPrice;
    }

    public int index() {
        return index;
    }

    public LocalDateTime startTime() {
        return startTime;
    }

    public String title() {
        return title;
    }

    public Duration runningTime() {
        return runningTime;
    }

    public double price() {
        return price;
    }

    public double finalPrice() {
        return finalPrice;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ShowDetail) obj;
        return this.index == that.index &&
                Objects.equals(this.startTime, that.startTime) &&
                Objects.equals(this.title, that.title) &&
                Objects.equals(this.runningTime, that.runningTime) &&
                Double.doubleToLongBits(this.price) == Double.doubleToLongBits(that.price) &&
                Double.doubleToLongBits(this.finalPrice) == Double.doubleToLongBits(that.finalPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, startTime, title, runningTime, price, finalPrice);
    }

    @Override
    public String toString() {
        return "ShowDetail[" +
                "index=" + index + ", " +
                "startTime=" + startTime + ", " +
                "title=" + title + ", " +
                "runningTime=" + runningTime + ", " +
                "price=" + price + ", " +
                "finalPrice=" + finalPrice + ']';
    }

}
