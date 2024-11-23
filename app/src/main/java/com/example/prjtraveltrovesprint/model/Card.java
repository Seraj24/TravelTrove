package com.example.prjtraveltrovesprint.model;

import androidx.annotation.NonNull;

public class Card {

    int image;
    String title;
    private String contentDescription;

    public Card() {

    }

    public Card(int image, String title, String contentDescription) {
        this.image = image;
        this.title = title;
        this.contentDescription = contentDescription;

    }


    public String getContentDescription() {
        return contentDescription;
    }

    public void setContentDescription(String contentDescription) {
        this.contentDescription = contentDescription;
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

    @NonNull
    @Override
    public String toString() {
        return "Card{" +
                "image=" + getImage() +
                ", title='" + getTitle() + '\'' +
                ", contentDescription='" + contentDescription + '\'' +
                '}';
    }

}
