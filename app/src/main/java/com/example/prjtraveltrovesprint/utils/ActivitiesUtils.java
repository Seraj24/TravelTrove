package com.example.prjtraveltrovesprint.utils;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


import androidx.cardview.widget.CardView;

import com.example.prjtraveltrovesprint.interfaces.ActivityEssentials;
import com.example.prjtraveltrovesprint.model.Airline;
import com.example.prjtraveltrovesprint.model.AirlineBooking;
import com.example.prjtraveltrovesprint.model.Booking;
import com.example.prjtraveltrovesprint.model.Card;
import com.example.prjtraveltrovesprint.model.Destination;
import com.example.prjtraveltrovesprint.model.Hotel;
import com.example.prjtraveltrovesprint.model.HotelBooking;
import com.example.prjtraveltrovesprint.model.TripPackage;

import java.util.HashMap;

public class ActivitiesUtils {

    public static void returnToPreviousView(Activity activity) {
        activity.finish();
    }

    public static void returnToViewAndClear(Context activity, Intent intent) {
        // Make sure to clean the previous activities above this one since we are not going to use
        // them, to help manage the memory
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        activity.startActivity(intent);
    }

    public static Booking retrieveBooking(Activity activity) {
        String errorTag = "RETRIEVING Trip Package FROM " + activity.getClass();
        Booking booking = null;
        Intent intent = activity.getIntent();
        if (intent != null) {
            booking = (Booking) intent.getSerializableExtra("booking");
        }

        if (booking == null) {
            Log.e(errorTag, "No booking found");
            activity.finish();
        }

        return booking;

    }

    public static TripPackage retrieveTripPackage(Activity activity) {
        String errorTag = "RETRIEVING Trip Package FROM " + activity.getClass();
        TripPackage tripPackage = null;
        Intent intent = activity.getIntent();
        if (intent != null) {
            tripPackage = (TripPackage) intent.getSerializableExtra("trip_package");
        }

        if (tripPackage == null) {
            Log.e(errorTag, "No trip package found");
            activity.finish();
        }

        return tripPackage;
    }

    public static HotelBooking retrieveHotelBooking(Activity activity) {
        String errorTag = "RETRIEVING Hotel Booking FROM " + activity.getClass();
        HotelBooking hotelBooking = null;
        Intent intent = activity.getIntent();
        if (intent != null) {
            hotelBooking = (HotelBooking) intent.getSerializableExtra("hotel_booking");
        }

        if (hotelBooking == null) {
            Log.e(errorTag, "No hotel booking found");
            activity.finish();
        }

        return hotelBooking;

    }

    public static AirlineBooking retrieveAirlineBooking(Activity activity) {
        String errorTag = "RETRIEVING Airline Booking FROM " + activity.getClass();
        AirlineBooking airlineBooking = null;
        Intent intent = activity.getIntent();
        if (intent != null) {
            airlineBooking = (AirlineBooking) intent.getSerializableExtra("airline_booking");
        }

        if (airlineBooking == null) {
            Log.e(errorTag, "No airline booking found");
            activity.finish();
        }

        return airlineBooking;

    }





}
