package com.example.prjtraveltrovesprint.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Destination implements Serializable {

    public enum DestinationType {
        PARIS, TOKYO, NEW_YORK, ROME, LONDON, SYDNEY, BARCELONA
    }

    private int image;
    private String title;
    private DestinationType destinationType;

    public Destination() {

    }

    public Destination(int image, String title, DestinationType destinationType) {
        this.image = image;
        this.title = title;
        this.destinationType = destinationType;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DestinationType getDestinationType() {
        return destinationType;
    }

    public void setDestinationType(DestinationType destinationType) {
        this.destinationType = destinationType;
    }

    @NonNull
    @Override
    public String toString() {
        return "Destination{" +
                "image=" + image +
                ", title='" + title + '\'' +
                '}';
    }
}
