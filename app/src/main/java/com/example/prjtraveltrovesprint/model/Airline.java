package com.example.prjtraveltrovesprint.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Airline extends TravelService implements Serializable {

    private double cost;
    private int seatsCount;

    public Airline() {

    }

    public Airline(double cost, String name, int seatsCount) {

        super(name);
        this.cost = cost;
        this.seatsCount = seatsCount;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getSeatsCount() {
        return seatsCount;
    }

    public void setSeatsCount(int seatsCount) {
        this.seatsCount = seatsCount;
    }

    @NonNull
    @Override
    public String toString() {
        return "Airline{" + "cost=" + this.getCost() + ", name='" + super.getName() +
                ", availableSeats='" + this.getSeatsCount() + '\'' + '}';
    }


}
