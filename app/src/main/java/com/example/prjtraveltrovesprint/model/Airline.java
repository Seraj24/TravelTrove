package com.example.prjtraveltrovesprint.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Airline extends TravelService implements Serializable {
    private double cost;

    public Airline() {

    }

    public Airline(double cost, String name) {

        super(name);
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @NonNull
    @Override
    public String toString() {
        return "Airline{" + "cost=" + this.getCost() + ", name='" + super.getName() + '\'' + '}';
    }


}
