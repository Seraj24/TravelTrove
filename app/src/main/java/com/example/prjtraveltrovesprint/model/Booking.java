package com.example.prjtraveltrovesprint.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Booking implements Serializable {

    public enum BookingType {
        PACKAGE, HOTEL, AIRLINES
    }

    private BookingType bookingType;
    private Destination currentDestination;
    private int discount;

    public Booking() { }

    public Booking(BookingType bookingType, Destination currentDestination) {
        this.bookingType = bookingType;
        this.currentDestination = currentDestination;
    }

    public BookingType getBookingType() {
        return bookingType;
    }

    public void setBookingType(BookingType bookingType) {
        this.bookingType = bookingType;
    }

    public Destination getCurrentDestination() {
        return currentDestination;
    }

    public void setCurrentDestination(Destination currentDestination) {
        this.currentDestination = currentDestination;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    @NonNull
    @Override
    public String toString() {
        return "Booking Details: \n" +
                "Type: " + bookingType.name() + "\n" +
                "Destination: " + (currentDestination != null ? currentDestination.getTitle() : "Not specified") + "\n" +
                "--------------------------------------";
    }
}
