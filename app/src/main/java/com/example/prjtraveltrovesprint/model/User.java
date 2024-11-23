package com.example.prjtraveltrovesprint.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private ArrayList<Destination> destinations;

    public User() {

    }

    public User(int id, String firstName, String lastName, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public User(int id, String firstName, String lastName, String email, String password, ArrayList<Destination> destinations) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.destinations = destinations;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Destination> getDestinations() { return destinations; }

    public void setDestinations(ArrayList<Destination> destinations) { this.destinations = destinations; }

    @NonNull
    @Override
    public String toString() {
        return "User{id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", destinations=" + destinations.size() + " destinations}";
    }
}
