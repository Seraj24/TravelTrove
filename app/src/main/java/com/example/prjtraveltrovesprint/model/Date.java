package com.example.prjtraveltrovesprint.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;

public class Date implements Comparable<Date>, Serializable {

    public enum DateType {
        DEPARTURE, RETURN, OTHER
    }

    private int day, month, year;
    private DateType dateType;

    public Date() {

    }

    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.dateType = DateType.OTHER;
    }

    public Date(int day, int month, int year, DateType dateType) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.dateType = dateType;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public DateType getDateType() {
        return dateType;
    }

    public void setDateType(DateType dateType) {
        this.dateType = dateType;
    }

    public int getDateCount() {
        return year + month + day;
    }

    @Override
    public boolean equals(@Nullable Object obj) {

        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        Date date = (Date) obj;
        return day == date.day && month == date.month && year == date.year;
    }

    @NonNull
    @Override
    public String toString() {
        return day + "/" + month + "/" + year;
    }

    @Override
    public int compareTo(Date otherDate) {
        if (this.year != otherDate.year) {
            return Integer.compare(this.year, otherDate.year);
        }
        if (this.month != otherDate.month) {
            return Integer.compare(this.month, otherDate.month);
        }
        return Integer.compare(this.day, otherDate.day);
    }
}
