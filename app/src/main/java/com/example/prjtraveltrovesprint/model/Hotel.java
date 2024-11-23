package com.example.prjtraveltrovesprint.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

public class Hotel extends TravelService implements Serializable {

    private ArrayList<Room> rooms;

    public Hotel() {

    }

    public Hotel(String name, ArrayList<Room> rooms) {
        super(name);
        this.rooms = rooms;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    @NonNull
    @Override
    public String toString() {
        return "Hotel{" + "name='" + super.getName() + '\'' + '}';
    }
}
