package com.example.prjtraveltrovesprint.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import com.example.prjtraveltrovesprint.model.Date;

public class HotelBooking implements Serializable {

    private Hotel hotel;
    private Room room;
    private Date checkInDate, checkOutDate;
    private int roomsCount;
    private double totalCost;

    public HotelBooking() {

    }

    public HotelBooking(Hotel hotel, Room room, Date checkInDate, Date checkOutDate,
                        int roomsCount, double totalCost) {
        this.hotel = hotel;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.roomsCount = roomsCount;
        this.totalCost = totalCost;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public int getRoomsCount() {
        return roomsCount;
    }

    public void setRoomsCount(int roomsCount) {
        this.roomsCount = roomsCount;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getDate() { return checkInDate + " " + checkOutDate; }

    @NonNull
    @Override
    public String toString() {
        return "Hotel: " + (hotel != null ? hotel.getName() : "Not selected") + "\n" +
                "Room: " + (room != null ? room.getRoomType() : "Not selected") + "\n" +
                "Check-in Date: " + checkInDate.toString() + "\n" +
                "Check-out Date: " + checkOutDate.toString() + "\n" +
                "Rooms Count: " + roomsCount + "\n" +
                "Total Cost: " + totalCost;
    }
}
