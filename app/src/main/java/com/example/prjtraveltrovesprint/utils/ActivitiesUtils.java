package com.example.prjtraveltrovesprint.utils;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.prjtraveltrovesprint.DestinationActivity;
import com.example.prjtraveltrovesprint.model.Destination;
import com.example.prjtraveltrovesprint.model.TripPackage;

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

    public static Destination retrieveCurrentDestination(Activity activity) {
        String errorTag = "RETRIEVING CURRENT DESTINATION FROM " + activity.getClass();
        Destination currentDestination = null;
        Intent intent = activity.getIntent();
        if (intent != null) {
            currentDestination = (Destination) intent.getSerializableExtra("destination_details");
        }

        if (currentDestination == null) {
            Log.e(errorTag, "No destination details found: ");
            activity.finish();
        }

        return currentDestination;
    }

    public static TripPackage retrieveTripPackage(Activity activity) {
        String errorTag = "RETRIEVING Trip Package FROM " + activity.getClass();
        TripPackage tripPackage = null;
        Intent intent = activity.getIntent();
        if (intent != null) {
            tripPackage = (TripPackage) intent.getSerializableExtra("trip_package");
        }

        if (tripPackage == null) {
            Log.e(errorTag, "No trip package found: ");
            activity.finish();
        }

        return tripPackage;
    }

}
