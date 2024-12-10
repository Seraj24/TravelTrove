package com.example.prjtraveltrovesprint.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class AirlineBooking implements Serializable {

    private Airline airline;
    private Date departureDate;
    private Date returnDate;

    public AirlineBooking() {

    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }


    public String getDate() { return departureDate + " " + returnDate; }

    @NonNull
    @Override
    public String toString() {
        return "Airline: " + (airline != null ? airline.getName() : "Not selected") + "\n" +
                "Departure Date: " + (departureDate != null ? departureDate.toString() : "Not selected") + "\n" +
                "Return Date: " + (returnDate != null ? returnDate.toString() : "Not selected") + "\n" +
                "Ticket Price: " + (airline != null && airline.getCost() != 0 ? airline.getCost() : "Not selected");
    }

}
