package com.example.prjtraveltrovesprint.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class TripPackage implements Serializable {

    private String date;
    private int guests;
    private int days;
    private Hotel hotel;
    private Room room;
    private Airline airline;
    private List<String> additionalServices;

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

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    public List<String> getAdditionalServices() {
        return additionalServices;
    }

    public void setAdditionalServices(List<String> additionalServices) {
        this.additionalServices = additionalServices;
    }
}
