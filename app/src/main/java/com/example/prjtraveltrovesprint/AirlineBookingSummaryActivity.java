package com.example.prjtraveltrovesprint;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.prjtraveltrovesprint.interfaces.ActivityEssentials;
import com.example.prjtraveltrovesprint.model.Airline;
import com.example.prjtraveltrovesprint.model.AirlineBooking;
import com.example.prjtraveltrovesprint.model.BaseBookingSummaryActivity;
import com.example.prjtraveltrovesprint.utils.ActivitiesUtils;

import java.util.Locale;

public class AirlineBookingSummaryActivity extends BaseBookingSummaryActivity implements ActivityEssentials {

    AirlineBooking airlineBooking;

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
            airlineBooking = ActivitiesUtils.retrieveAirlineBooking(this);
            initialize();
        }
        catch (Exception e) {
            Log.e(LOG_TAG, "Error while initializing booking summary activity: " + e.getMessage(), e);
            Toast.makeText(this, "Failed to load booking summary.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void initialize() {

        initializeViews();
        hideUnnecessaryViews(booking);

        String emptySpace = " ", dollarSign = "$";
        int travelers = 1;

        String txtPackage = tvPackage.getText().toString() +
                emptySpace + currentDestination.getTitle();
        String txtTravelers = tvTravelersCount.getText().toString() +
                emptySpace + travelers;
        String txtTripDate = tvTripDate.getText().toString() +
                 airlineBooking.getDate();
        String txtAirline = tvAirline.getText().toString() + emptySpace +
                airlineBooking.getAirline().getName();
        String txtTotalCost = String.format(Locale.US, "Hotel Cost: "
                + dollarSign + airlineBooking.getAirline().getCost());

        tvPackage.setText(txtPackage);
        tvTripDate.setText(txtTripDate);
        tvTravelersCount.setText(txtTravelers);
        tvAirline.setText(txtAirline);
        tvTotalCost.setText(txtTotalCost);

        //nextBtn.setOnClickListener(v -> launchConfirmBookingActivity());

    }

    @Override
    protected void launchConfirmBookingActivity() {
        Intent intent = new Intent(AirlineBookingSummaryActivity.this, ConfirmBookingActivity.class);
        intent.putExtra("booking", booking);
        intent.putExtra("airline_booking", airlineBooking);
        startActivity(intent);


    }

}