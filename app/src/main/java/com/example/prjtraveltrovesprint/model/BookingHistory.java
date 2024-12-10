package com.example.prjtraveltrovesprint.model;

public class BookingHistory {
    private String destination;
    private String bookingType;
    private String bookingDetails;

    public BookingHistory() {}

    public BookingHistory(String destination, String bookingType, String bookingDetails) {
        this.destination = destination;
        this.bookingType = bookingType;
        this.bookingDetails = bookingDetails;
    }

    // Getters and setters
    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getBookingType() {
        return bookingType;
    }

    public void setBookingType(String bookingType) {
        this.bookingType = bookingType;
    }

    public String getBookingDetails() {
        return bookingDetails;
    }

    public void setBookingDetails(String bookingDetails) {
        this.bookingDetails = bookingDetails;
    }
}

