package com.example.prjtraveltrovesprint.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class TravelService implements Serializable {

    private String name;

    public TravelService() {

    }

    public TravelService(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() {
        return "TravelService{" + ", name='" + name + '\'' + '}';
    }
}
