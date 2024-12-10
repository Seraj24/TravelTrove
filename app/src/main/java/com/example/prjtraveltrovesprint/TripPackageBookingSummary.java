package com.example.prjtraveltrovesprint;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.prjtraveltrovesprint.interfaces.ActivityEssentials;
import com.example.prjtraveltrovesprint.model.BaseBookingSummaryActivity;
import com.example.prjtraveltrovesprint.model.Booking;
import com.example.prjtraveltrovesprint.model.Destination;
import com.example.prjtraveltrovesprint.model.TripPackage;
import com.example.prjtraveltrovesprint.utils.ActivitiesUtils;

import java.util.Locale;

public class TripPackageBookingSummary extends BaseBookingSummaryActivity implements ActivityEssentials {


    TripPackage tripPackage;
    Destination currentDestination;

    int guestsCount;
    double hotelCost, airlineCost, totalCost = -1;
    int discount;

    private static final ActivityName ACTIVITY_NAME = ActivityName.BOOKING_SUMMARY;
    private static final String LOG_TAG = ACTIVITY_NAME + " ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_booking_summary);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.booking_summary), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        try {
            booking = ActivitiesUtils.retrieveBooking(this);
            currentDestination = booking.getCurrentDestination();
            tripPackage = ActivitiesUtils.retrieveTripPackage(this);

            initialize();
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error while initializing booking summary activity: " + e.getMessage(), e);
            Toast.makeText(this, "Failed to load booking summary.", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    public void initialize() {

        initializeViews();

        String emptySpace = " ", dollarSign = "$";

        String additionalServices = getAdditionalServices().isEmpty() ? "None"
                : getAdditionalServices();
        guestsCount = tripPackage.getGuests();
        double totalCost = calculateCost();

        String packageTxt = tvPackage.getText().toString() + emptySpace + currentDestination.getTitle();
        String tripDateTxt = tvTripDate.getText().toString() + emptySpace + tripPackage.getDate();
        String guestsCountTxt = tvTravelersCount.getText().toString() + emptySpace + guestsCount;
        String airlineTxt = tvAirline.getText().toString() + emptySpace + tripPackage.getAirlineBooking().getAirline().getName();
        String hotelTxt = tvHotel.getText().toString() + emptySpace + tripPackage.getHotelBooking().getHotel().getName();
        String servicesTxt = tvServices.getText().toString() + emptySpace + additionalServices;

        String totalCostTxt = String.format(Locale.US, "Hotel Cost (%d nights): ",
                tripPackage.getDays()) + dollarSign + hotelCost + "\n\nAirline Cost (Two-way): " +
                dollarSign + airlineCost + "\n/Per Person\n\n" + tvTotalCost.getText().toString() +
                emptySpace + dollarSign + totalCost + " for " + guestsCount + " guest/s";


        tvPackage.setText(packageTxt);
        tvTripDate.setText(tripDateTxt);
        tvTravelersCount.setText(guestsCountTxt);
        tvAirline.setText(airlineTxt);
        tvHotel.setText(hotelTxt);
        tvServices.setText(servicesTxt);
        tvTotalCost.setText(totalCostTxt);

    }

    @Override
    protected void launchConfirmBookingActivity() {
        if (totalCost != -1) {
            tripPackage.setTotalCost(totalCost);
            Intent intent = new Intent(TripPackageBookingSummary.this, ConfirmBookingActivity.class);
            intent.putExtra("booking", booking);
            intent.putExtra("trip_package", tripPackage);
            startActivity(intent);
        }

    }

    private String getAdditionalServices() {
        return String.join(", ", tripPackage.getAdditionalServices());
    }


    private double calculateCost() {
        hotelCost = tripPackage.getHotelBooking().getRoom().getCost() * tripPackage.getDays();
        airlineCost = tripPackage.getAirlineBooking().getAirline().getCost();
        totalCost = (hotelCost + airlineCost) * guestsCount;
        discount = booking.getDiscount();
        if (discount > 0)
            totalCost = totalCost * (discount / 100.0);

        return totalCost;
    }
}