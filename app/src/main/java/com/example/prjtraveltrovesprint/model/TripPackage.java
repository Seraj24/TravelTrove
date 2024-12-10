package com.example.prjtraveltrovesprint.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class TripPackage implements Serializable {

    private String date;
    private int guests;
    private int days;
    private HotelBooking hotelBooking;
    private AirlineBooking airlineBooking;
    private List<String> additionalServices;
    private double totalCost;

    public TripPackage() {

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getGuests() {
        return guests;
    }

    public void setGuests(int guests) {
        this.guests = guests;
    }

    public int getDays() { return days; }

    public void setDays(int days) {
        this.days = days;
    }


    public HotelBooking getHotelBooking() {
        return hotelBooking;
    }

    public void setHotelBooking(HotelBooking hotelBooking) {
        this.hotelBooking = hotelBooking;
    }

    public AirlineBooking getAirlineBooking() {
        return airlineBooking;
    }

    public void setAirlineBooking(AirlineBooking airlineBooking) {
        this.airlineBooking = airlineBooking;
    }

    public List<String> getAdditionalServices() {
        return additionalServices;
    }

    public void setAdditionalServices(List<String> additionalServices) {
        this.additionalServices = additionalServices;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    @NonNull
    @Override
    public String toString() {
        return "Date: " + date + "\n" +
                "Travelers: " + guests + "\n" +
                "Duration: " + days + " days\n" +
                (hotelBooking != null ? "Hotel: " + hotelBooking.getHotel().getName() + "\n" : "Hotel: Not selected\n") +
                (hotelBooking.getRoom() != null ? "Room: " + hotelBooking.getRoom().getRoomType() + "\n" : "Room: Not selected\n") +
                (airlineBooking != null ? "Airline: " + airlineBooking.getAirline().getName() + "\n" : "Airline: Not selected\n") +
                (additionalServices != null && !additionalServices.isEmpty() ? "Additional Services: "
                        + String.join(", ", additionalServices) + "\n" : "Additional Services: None\n")
                + "Total Cost: " + totalCost;
    }
}
