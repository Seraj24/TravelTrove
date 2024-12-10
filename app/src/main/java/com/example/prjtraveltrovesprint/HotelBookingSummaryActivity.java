package com.example.prjtraveltrovesprint;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.prjtraveltrovesprint.interfaces.ActivityEssentials;
import com.example.prjtraveltrovesprint.model.BaseBookingSummaryActivity;
import com.example.prjtraveltrovesprint.model.Hotel;
import com.example.prjtraveltrovesprint.model.HotelBooking;
import com.example.prjtraveltrovesprint.utils.ActivitiesUtils;

import java.util.ArrayList;
import java.util.Locale;

public class HotelBookingSummaryActivity extends BaseBookingSummaryActivity implements ActivityEssentials {

    private HotelBooking hotelBooking;
    private Hotel hotel;

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
            hotelBooking = ActivitiesUtils.retrieveHotelBooking(this);
            hotel = hotelBooking.getHotel();
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

        String txtPackage = tvPackage.getText().toString() +
                emptySpace + currentDestination.getTitle();
        String txtTripDate = tvTripDate.getText().toString() +
                hotelBooking.getDate();
        String txtHotel = tvHotel.getText().toString() + emptySpace +
                hotel.getName();
        String txtTotalCost = String.format(Locale.US, "Hotel Cost: "
                 + dollarSign + hotelBooking.getTotalCost());

        tvPackage.setText(txtPackage);
        tvTripDate.setText(txtTripDate);
        tvHotel.setText(txtHotel);
        tvTotalCost.setText(txtTotalCost);

        //nextBtn.setOnClickListener(v -> launchConfirmBookingActivity());

    }

    @Override
    protected void launchConfirmBookingActivity() {
        Intent intent = new Intent(HotelBookingSummaryActivity.this, ConfirmBookingActivity.class);
        intent.putExtra("booking", booking);
        intent.putExtra("hotel_booking", hotelBooking);
        startActivity(intent);

    }



}